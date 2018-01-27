package com.xinyunlian.jinfu.creditline.service;

import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.calc.service.LoanCalcService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.creditline.dao.LoanUserCreditLineDao;
import com.xinyunlian.jinfu.creditline.dto.LoanUserCreditLineDto;
import com.xinyunlian.jinfu.creditline.entity.LoanUserCreditLinePo;
import com.xinyunlian.jinfu.creditline.enums.ELoanUserCreditLineStatus;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.schedule.dao.ScheduleDao;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.entity.SchedulePo;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import com.xinyunlian.jinfu.schedule.service.InnerScheduleService;
import com.ylfin.eye.dto.CreditDto;
import com.ylfin.eye.enums.CreditStatus;
import com.ylfin.eye.service.CreditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ConnectionUtils;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author willwang
 */
@Service
public class LoanUserCreditLineServiceImpl implements LoanUserCreditLineService {

    @Autowired
    private LoanUserCreditLineDao loanUserCreditLineDao;

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private LoanCalcService loanCalcService;

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private InnerScheduleService innerScheduleService;

    @Autowired
    private CreditService creditService;

    @Autowired
    private QueueSender queueSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanUserCreditLineService.class);

    @Override
    public LoanUserCreditLineDto get(String userId) {
        LoanUserCreditLinePo po = loanUserCreditLineDao.findOne(userId);

        if (po == null) {
            po = new LoanUserCreditLinePo();
            po.setTotal(BigDecimal.ZERO);
            po.setAvailable(BigDecimal.ZERO);
            po.setDynamic(BigDecimal.ZERO);
            po.setStatus(ELoanUserCreditLineStatus.NOT_EXISTS);

            return ConverterService.convert(po, LoanUserCreditLineDto.class);
        }

        LoanUserCreditLineDto dto = ConverterService.convert(po, LoanUserCreditLineDto.class);
        dto.setDueDate("");

        dto = this.completeLoanData(userId, dto);

        //四舍五入
        dto.setShould(NumberUtil.roundTwo(dto.getShould()));
        dto.setTotal(NumberUtil.roundTwo(dto.getTotal()));
        dto.setAvailable(NumberUtil.roundTwo(dto.getAvailable()));

        return dto;
    }

    @Override
    public HashMap<String, BigDecimal> getAvaliable(Collection<String> userIds) {

        HashMap<String, BigDecimal> rs = new HashMap<>();

        if (userIds.size() <= 0) {
            return rs;
        }

        List<LoanUserCreditLinePo> pos = loanUserCreditLineDao.findByUserIdIn(userIds);

        if (pos.size() > 0) {
            for (LoanUserCreditLinePo po : pos) {
                rs.put(po.getUserId(), po.getAvailable());
            }
        }


        return rs;
    }

    @Override
    public void apply(String userId) {
        LoanUserCreditLinePo po = loanUserCreditLineDao.findOne(userId);
        if (po == null) {
            po = new LoanUserCreditLinePo();
            po.setStatus(ELoanUserCreditLineStatus.CALCULATING);
            po.setAvailable(BigDecimal.ZERO);
            po.setDynamic(BigDecimal.ZERO);
            po.setTotal(BigDecimal.ZERO);
            po.setUserId(userId);
            loanUserCreditLineDao.save(po);
        }

        //通知给风控系统进行额度申请
        queueSender.send(DestinationDefine.CREDIT_LINE_APPLY, userId);
    }

    @Override
    public void credit(String userId, BigDecimal creditLine) {
        LoanUserCreditLinePo po = loanUserCreditLineDao.findOne(userId);
        if (po == null) {
            po = new LoanUserCreditLinePo();
            po.setStatus(ELoanUserCreditLineStatus.UNUSED);
            po.setAvailable(creditLine);
            po.setDynamic(BigDecimal.ZERO);
            po.setUserId(userId);
        }
        po.setTotal(creditLine);

        loanUserCreditLineDao.save(po);
    }

    @Override
    @Transactional
    public void use(String userId) throws BizServiceException {
        LoanUserCreditLinePo loanUserCreditLinePo = loanUserCreditLineDao.findByUserId(userId);

        if(loanUserCreditLinePo == null){
            return;
        }

        loanUserCreditLinePo.setAvailable(BigDecimal.ZERO);
        loanUserCreditLinePo.setStatus(ELoanUserCreditLineStatus.USED);

        loanUserCreditLineDao.save(loanUserCreditLinePo);
    }

    @Override
    @Transactional
    public void back(String userId) throws BizServiceException {

        LoanUserCreditLinePo loanUserCreditLinePo = loanUserCreditLineDao.findByUserId(userId);

        if(loanUserCreditLinePo == null){
            return;
        }

        loanUserCreditLinePo.setAvailable(loanUserCreditLinePo.getTotal());
        loanUserCreditLinePo.setStatus(ELoanUserCreditLineStatus.UNUSED);

        loanUserCreditLineDao.save(loanUserCreditLinePo);
    }

    @Override
    @JmsListener(destination = DestinationDefine.CREDIT_LINE_UPDATED_EVENT)
    public void updated(String userId) throws BizServiceException {
        //如果B不是已结清，则直接不处理了
        List<LoanDtlPo> loanDtlPos = loanDtlDao.findByUserId(userId);

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("update user credit line, user_id:{},", userId);
        }

        if(!loanDtlPos.isEmpty()){
            for (LoanDtlPo loanDtlPo : loanDtlPos) {
                if("B".equals(loanDtlPo.getTestSource()) && loanDtlPo.getLoanStat() != ELoanStat.PAID){
                    if(LOGGER.isDebugEnabled()){
                        LOGGER.debug("update user credit line has unPaid, user_id:{},", userId);
                    }
                    return;
                }
            }
        }

        this.updateWithoutCheck(userId);
    }

    @Override
    public void updateWithoutCheck(String userId) throws BizServiceException {
        CreditDto creditDto = creditService.getCredit(userId);

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("update user credit line, user_id:{},creditDto:{}", userId, creditDto);
        }

        if(creditDto != null){
            this.reset(userId, creditDto.getCredit(), this.getCreditStatus(creditDto.getCreditStatus()));
        }
    }

    /**
     * 收银台到小贷用户额度状态的转换
     * @param creditStatus
     * @return
     */
    private ELoanUserCreditLineStatus getCreditStatus(CreditStatus creditStatus){

        Map<CreditStatus, ELoanUserCreditLineStatus> map = new HashMap<>();

        map.put(CreditStatus.NORMAL, ELoanUserCreditLineStatus.UNUSED);
        map.put(CreditStatus.EXPIRED, ELoanUserCreditLineStatus.INVALID);
        map.put(CreditStatus.REFUSED, ELoanUserCreditLineStatus.INVALID);
        map.put(CreditStatus.PENDING, ELoanUserCreditLineStatus.CALCULATING);
        map.put(CreditStatus.ERROR, ELoanUserCreditLineStatus.INVALID);

        return map.get(creditStatus);
    }

    @Override
    @Transactional
    public void reset(String userId, BigDecimal amt, ELoanUserCreditLineStatus status) throws BizServiceException {

        LoanUserCreditLinePo loanUserCreditLinePo = loanUserCreditLineDao.findByUserId(userId);

        if(loanUserCreditLinePo == null){
            return;
        }

        loanUserCreditLinePo.setTotal(amt);
        loanUserCreditLinePo.setAvailable(amt);
        loanUserCreditLinePo.setStatus(status);

        loanUserCreditLineDao.save(loanUserCreditLinePo);
    }

    private LoanUserCreditLineDto completeLoanData(String userId, LoanUserCreditLineDto dto) {

        List<LoanDtlPo> loanDtlPos = loanDtlDao.findByUserId(userId);

        LoanDtlPo latest = null;

        if (loanDtlPos.size() > 0) {
            for (LoanDtlPo loanDtlPo : loanDtlPos) {
                if ("B".equals(loanDtlPo.getTestSource()) && loanDtlPo.getLoanStat() != ELoanStat.PAID) {
                    latest = loanDtlPo;
                }
            }
        }

        LoanCalcDto calc = null;

        if (latest != null) {
            switch (latest.getRepayMode()) {
                //随借随还，本期还款日 = 贷款的到期日， 本期应还金额=应还本金+利息+罚金
                case INTR_PER_DIEM:
                    dto.setDueDate(latest.getDutDate());
                    calc = loanCalcService.any(userId, latest.getLoanId(), null, new Date());
                    break;
                //等额本息,本期还款日 = 如果逾期,算最后一次逾期，如果没逾期算当期所在
                case MONTH_AVE_CAP_PLUS_INTR:

                    List<SchedulePo> schedules = scheduleDao.findByLoanId(latest.getLoanId());

                    SchedulePo targetSchedule = null;

                    if (schedules.size() > 0) {
                        for (SchedulePo schedule : schedules) {
                            if (schedule.getScheduleStatus() == EScheduleStatus.OVERDUE) {
                                targetSchedule = schedule;
                            }
                        }
                    }

                    if (targetSchedule == null) {
                        ScheduleDto current = null;
                        try {
                            current = innerScheduleService.getCurrentSchedule(latest.getLoanId());
                        } catch (Exception e) {
                            LOGGER.error("get current schedule error", e);
                        }

                        targetSchedule = ConverterService.convert(current, SchedulePo.class);
                    }


                    if (targetSchedule != null) {
                        calc = loanCalcService.period(userId, latest.getLoanId(), targetSchedule.getSeqNo(), new Date());
                        dto.setDueDate(targetSchedule.getDueDate());
                    }


                    break;
                default:
                    break;
            }
        }


        if (calc != null) {
            dto.setShould(
                    calc.getCapital()
                            .add(calc.getInterest())
                            .add(calc.getFine())
                            .add(calc.getFee())
            );
        }

        return dto;
    }

}
