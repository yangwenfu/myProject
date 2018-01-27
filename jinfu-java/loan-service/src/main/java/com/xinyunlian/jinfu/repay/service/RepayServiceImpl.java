package com.xinyunlian.jinfu.repay.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xinyunlian.jinfu.acct.service.InnerAcctReserveService;
import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.calc.service.LoanAvgCapAvgIntrCalcService;
import com.xinyunlian.jinfu.calc.service.LoanCalcService;
import com.xinyunlian.jinfu.cashier.dto.LoanPayDto;
import com.xinyunlian.jinfu.cashier.service.LoanPayService;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.sms.SmsUtil;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponDto;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponRepayDto;
import com.xinyunlian.jinfu.coupon.service.LoanCouponService;
import com.xinyunlian.jinfu.creditline.service.LoanUserCreditLineService;
import com.xinyunlian.jinfu.depository.dto.LoanDepositoryAcctDto;
import com.xinyunlian.jinfu.depository.service.LoanDepositoryAcctService;
import com.xinyunlian.jinfu.loan.dao.LoanApplDao;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.dto.repay.LinkRepayDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanRepayLinkDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.entity.RepayDtlPo;
import com.xinyunlian.jinfu.loan.enums.*;
import com.xinyunlian.jinfu.loan.service.InnerLinkRepayService;
import com.xinyunlian.jinfu.loan.service.InnerLoanService;
import com.xinyunlian.jinfu.loan.service.InnerRepayService;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthPreviewDto;
import com.xinyunlian.jinfu.overdue.service.InnerLoanOverdueService;
import com.xinyunlian.jinfu.overdue.service.LoanOverdueService;
import com.xinyunlian.jinfu.pay.dao.PayRecvOrdDao;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.entity.PayRecvOrdPo;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.pay.service.InnerPayRecvOrdService;
import com.xinyunlian.jinfu.repay.ERefundFlag;
import com.xinyunlian.jinfu.repay.dao.RepayDao;
import com.xinyunlian.jinfu.repay.dto.ExternalRepayCallbackDto;
import com.xinyunlian.jinfu.repay.dto.LoanPreRepayMiddleDto;
import com.xinyunlian.jinfu.repay.dto.LoanRepayRespDto;
import com.xinyunlian.jinfu.repay.dto.req.RepayReqDto;
import com.xinyunlian.jinfu.repay.dto.resp.LoanCashierCallbackDto;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.schedule.dao.ScheduleDao;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.entity.SchedulePo;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import com.xinyunlian.jinfu.schedule.service.InnerScheduleService;
import com.ylfin.depository.dto.RepayDto;
import com.ylfin.depository.service.TransactionService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author willwang
 */
@Service
public class RepayServiceImpl implements RepayService {

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private LoanApplDao loanApplDao;

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private RepayDao repayDao;

    @Autowired
    private InnerScheduleService innerScheduleService;

    @Autowired
    private InnerLinkRepayService innerLinkRepayService;

    @Autowired
    private InnerPayRecvOrdService innerPayRecvOrdService;

    @Autowired
    private InnerLoanOverdueService innerLoanOverdueService;

    @Autowired
    private InnerLoanService innerLoanService;

    @Autowired
    private InnerAcctReserveService innerAcctReserveService;

    @Autowired
    private InnerRepayService innerRepayService;

    @Autowired
    private LoanCalcService loanCalcService;

    @Autowired
    private LoanAvgCapAvgIntrCalcService loanAvgCapAvgIntrCalcService;

    @Autowired
    private LoanCouponService loanCouponService;

    @Autowired
    private PayRecvOrdDao payRecvOrdDao;

    @Autowired
    private LoanOverdueService loanOverdueService;

    @Autowired
    private LoanPayService loanPayService;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Autowired
    private LoanUserCreditLineService loanUserCreditLineService;

    @Autowired
    private LoanDepositoryAcctService loanDepositoryAcctService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private QueueSender queueSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(RepayService.class);

    @Override
    @Transactional
    public RepayDtlDto save(RepayDtlDto repayDtlDto) {
        RepayDtlPo po;
        if (repayDtlDto.getRepayId() == null) {
            po = new RepayDtlPo();
        } else {
            po = repayDao.findOne(repayDtlDto.getRepayId());
        }

        if (po.getCreateTs() != null) {
            po.setVersionCt(0L);
        }
        ConverterService.convert(repayDtlDto, po);
        repayDao.save(po);
        repayDtlDto.setRepayId(po.getRepayId());
        return repayDtlDto;
    }

    @Override
    public RepayDtlDto inAdvanceRepay(String userId, RepayReqDto request, EPrType prType) throws BizServiceException {
        LoanCalcDto calc = this.calc(userId, request);
        return this.inAdvanceRepayWithCalc(userId, request, prType, calc);
    }

    @Override
    @Transactional
    public RepayDtlDto inAdvanceRepayWithCalc(String userId, RepayReqDto request, EPrType prType, LoanCalcDto calc) throws BizServiceException {
        LoanDtlPo loanDtlPo = loanDtlDao.findOne(request.getLoanId());
        LoanDtlDto loan = ConverterService.convert(loanDtlPo, LoanDtlDto.class);

        assertRepayRequest(userId, request, loan);
        assertRepaying(request);
        assertLoanNotPaid(loan);
        assertAmtNotZero(request);

        if (request.getAmt() != null) {
            request.setAmt(NumberUtil.roundTwo(request.getAmt()));
        }

        LoanPreRepayMiddleDto middle = null;

        //更新占用额度
        loanDtlPo.setRsrvAmt(loanDtlPo.getRsrvAmt().add(calc.getCapital()));
        assertValidRepayAmt(loanDtlPo);
        loanDtlDao.save(loanDtlPo);

        //生成还款记录
        RepayDtlPo repayDtlPo = this.saveRepayDtl(loan, calc, prType, request);
        //生成payRecv
        this.savePayRecv(loan, repayDtlPo, prType, calc);

        switch (request.getRepayType()) {
            case PRE_ALL:
            case PRE_CURRENT:
                middle = this.listPreRepayMiddle(request.getRepayType(), repayDtlPo, loan);
                break;
            case DAY:
                middle = this.listDayRepayMiddle(repayDtlPo, loan);
                break;
            case OVERDUE:
                middle = this.listOverdueRepayMiddle(repayDtlPo, loan);
                break;
            case PERIOD:
                middle = this.listPeriodRepayMiddle(request, repayDtlPo, loan);
                break;
            default:
                break;
        }

        if (middle == null || CollectionUtils.isEmpty(middle.getSchedules()) || CollectionUtils.isEmpty(middle.getLinks())) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "inAdvanceRepay Error");
        }

        //生成link
        for (LoanRepayLinkDto link : middle.getLinks()) {
            innerLinkRepayService.add(link);
        }

        return ConverterService.convert(repayDtlPo, RepayDtlDto.class);
    }

    @Override
    public List<LoanRepayRespDto> list(String loanId) {
        List<RepayDtlPo> list = repayDao.getRepayedList(loanId);

        List<LoanRepayRespDto> rs = new ArrayList<>();

        for (RepayDtlPo po : list) {
            LoanRepayRespDto resp = new LoanRepayRespDto();
            resp.setRepayDate(DateHelper.formatDate(po.getCreateTs(), DateHelper.SIMPLE_DATE_YMDHM_CN));
            resp.setRepayStatus(po.getStatus());
            resp.setTransMode(po.getTransMode());
            resp.setRepayId(po.getRepayId());

            BigDecimal capital = AmtUtils.positive(po.getRepayPrinAmt());
            BigDecimal interest = AmtUtils.positive(po.getRepayIntr());
            BigDecimal fine = AmtUtils.positive(po.getRepayFine());
            BigDecimal fee = AmtUtils.positive(po.getRepayFee());
            resp.setCapital(capital);
            resp.setInterest(interest);
            resp.setFine(fine);
            resp.setFee(fee);

            resp = this.completeCoupon(resp, po.getRepayId());

            BigDecimal couponPrice = BigDecimal.ZERO;
            if (resp.getCoupon() != null && resp.getCoupon().getPrice() != null) {
                couponPrice = resp.getCoupon().getPrice();
            }

            resp.setAmt(
                    AmtUtils.positive(capital.add(interest).add(fine).add(fee).subtract(couponPrice))
            );
            rs.add(resp);
        }

        return rs;
    }

    @Override
    public RepayDtlDto get(String repayId) {
        RepayDtlPo repayDtlPo = repayDao.findOne(repayId);
        return ConverterService.convert(repayDtlPo, RepayDtlDto.class);
    }

    @Override
    public List<RepayDtlDto> listByLoanId(String loanId) {
        List<RepayDtlPo> list = repayDao.getRepayedList(loanId);
        return ConverterService.convertToList(list, RepayDtlDto.class);
    }

    @Override
    public List<RepayDtlDto> findByLoanIds(Collection<String> loanIds) {
        List<RepayDtlDto> rs = new ArrayList<>();

        if (loanIds.size() <= 0) {
            return rs;
        }

        List<RepayDtlPo> list = repayDao.findByLoanIdIn(loanIds);
        list.forEach(item -> rs.add(ConverterService.convert(item, RepayDtlDto.class)));

        return rs;
    }

    @Override
    public void overdueJob() {

        //查询出所有未结清的过去的单子
        int currentPage = 1;
        int size = 1000;

        LOGGER.info("start update overdue job");

        String nowDate = DateHelper.getWorkDate();

        if (AppConfigUtil.isDevEnv()) {
            nowDate = "2017-2-1";
        }

        List<SchedulePo> list;
        do {
            LOGGER.info(String.format("currentPage:%s", currentPage));
            list = this.getOverdueList(nowDate, currentPage++, size);
            for (SchedulePo schedulePo : list) {
                try {
                    schedulePo.setScheduleStatus(EScheduleStatus.OVERDUE);
                    LoanDtlPo loanDtlPo = loanDtlDao.findOne(schedulePo.getLoanId());
                    loanDtlPo.setLoanStat(ELoanStat.OVERDUE);
                    loanDtlDao.save(loanDtlPo);
                    scheduleDao.save(schedulePo);
                    LOGGER.info(String.format("scheduleId:%s,loanId:%s update success",
                            schedulePo.getScheduleId(), schedulePo.getLoanId()));
                } catch (Exception e) {
                    LOGGER.warn(String.format("scheduleId:%s,loanId:%s update fail",
                            schedulePo.getScheduleId(), schedulePo.getLoanId()), e);
                }
            }
        } while (!list.isEmpty());

        LOGGER.info("end update overdue job");
    }

    @Override
    public void overdueWithhold() {
        int current = 1, size = 200;
        List<LoanDtlDto> loanDtlDtos;
        do {
            loanDtlDtos = loanOverdueService.listOverdues(current++, size);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("overdue withhold, query result:{}", loanDtlDtos.size());
            }

            for (LoanDtlDto loanDtlDto : loanDtlDtos) {
                try {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("overdue withhold begin, loan_id:{}", loanDtlDto.getLoanId());
                    }

                    Integer financeSourceId = loanDtlDto.getFinanceSourceId() == null ? 1 : loanDtlDto.getFinanceSourceId();
                    FinanceSourceDto financeSourceDto = financeSourceService.findById(financeSourceId);
                    if (financeSourceDto.getType() != EFinanceSourceType.OWN) {
                        LOGGER.info("job:overdueWithhold,loan_id:{} is not own", loanDtlDto.getLoanId());
                        continue;
                    }


                    RepayReqDto request = new RepayReqDto();
                    request.setLoanId(loanDtlDto.getLoanId());
                    ERepayType repayType = null;
                    switch (loanDtlDto.getRepayMode()) {
                        case MONTH_AVE_CAP_PLUS_INTR:
                            repayType = ERepayType.OVERDUE;
                            break;
                        case INTR_PER_DIEM:
                            repayType = ERepayType.DAY;
                            break;
                    }
                    request.setRepayType(repayType);

                    this.withhold(null, EPrType.RECEIVE, request);

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("overdue withhold success, loan_id:{}", loanDtlDto.getLoanId());
                    }
                } catch (Exception e) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.warn("overdue withhold, occur exception", e);
                    }
                }
            }
        } while (!loanDtlDtos.isEmpty());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("overdue withhold finish");
        }

    }

    @Override
    @Transactional
    public void repay(RepayReqDto request, RepayDtlDto repay, LoanCashierCallbackDto callback, String mobile) throws BizServiceException {
        PayRecvOrdDto payRecvOrdDto = innerPayRecvOrdService.findByBizId(repay.getRepayId());

        payRecvOrdDto = this.convertCashierCallback(payRecvOrdDto, callback);
        innerPayRecvOrdService.save(payRecvOrdDto);

        //扣款成功和余额不足的短信
        Map<String, String> params = new HashMap<>();
        params.put("totalAmt", NumberUtil.roundTwo(payRecvOrdDto.getTrxAmt()).toString());

        switch (payRecvOrdDto.getOrdStatus()) {
            case SUCCESS:
                this.repaySuccess(request, repay, payRecvOrdDto);
                //如果是代扣需要发送短信
                if (this.canSendMessage(repay, mobile) && "0000".equals(payRecvOrdDto.getRetCode())) {
                    LOGGER.info("send sms 113 to user:{}", mobile);
                    SmsUtil.send("113", params, mobile);
                }
                break;
            case FAILED:
                this.repayFailed(repay);
                if (this.canSendMessage(repay, mobile) && "C6".equals(payRecvOrdDto.getRetCode())) {
                    LOGGER.info("send sms 114 to user:{}", mobile);
                    SmsUtil.send("114", params, mobile);
                }
                break;
            default:
                break;
        }
    }

    @Override
    @Transactional
    public void externalInAdvance(String loanId) throws BizServiceException {

        //找出所有非结清、非逾期的还款计划生成还款记录
        List<SchedulePo> schedulePos = scheduleDao.getRepayedList(loanId);

        LoanDtlPo loanDtlPo = loanDtlDao.findOne(loanId);

        BigDecimal total = BigDecimal.ZERO;

        if (schedulePos.size() > 0) {

            for (SchedulePo schedulePo : schedulePos) {
                if (!EScheduleStatus.NOTYET.equals(schedulePo.getScheduleStatus())) {
                    continue;
                }
                BigDecimal capital = AmtUtils.positive(schedulePo.getShouldCapital().subtract(schedulePo.getActualCapital()));
                BigDecimal interest = AmtUtils.positive(schedulePo.getShouldInterest().subtract(schedulePo.getActualInterest()));

                //增加还款记录
                RepayDtlPo repayDtlPo = new RepayDtlPo();
                repayDtlPo.setAcctNo(loanDtlPo.getAcctNo());
                repayDtlPo.setLoanId(loanDtlPo.getLoanId());
                repayDtlPo.setRepayType(ERepayType.PERIOD);
                repayDtlPo.setStatus(ERepayStatus.PROCESS);
                repayDtlPo.setRepayPrinAmt(capital);
                repayDtlPo.setRepayIntr(interest);
                repayDtlPo.setRepayFine(BigDecimal.ZERO);
                repayDtlPo.setRepayFee(BigDecimal.ZERO);
                repayDtlPo.setRepayDateTime(new Date());
                repayDtlPo.setRepayDate(DateHelper.formatDate(repayDtlPo.getRepayDateTime(), ApplicationConstant.DATE_FORMAT));
                repayDtlPo.setTransMode(ETransMode.AUTO);
                repayDtlPo = repayDao.save(repayDtlPo);

                total = total.add(capital);

                //增加交易流水记录
                PayRecvOrdPo payRecvOrdPo = new PayRecvOrdPo();
                payRecvOrdPo.setOrdStatus(EOrdStatus.PROCESS);
                payRecvOrdPo.setBizId(repayDtlPo.getRepayId());
                payRecvOrdPo.setPrType(EPrType.RECEIVE);
                payRecvOrdPo.setAcctNo(loanDtlPo.getAcctNo());
                payRecvOrdPo.setTrxAmt(capital.add(interest));
                payRecvOrdPo.setTrxDate(repayDtlPo.getRepayDate());

                payRecvOrdDao.save(payRecvOrdPo);

                //增加关联
                LoanRepayLinkDto loanRepayLinkDto = new LoanRepayLinkDto();
                loanRepayLinkDto.setRepayId(repayDtlPo.getRepayId());
                loanRepayLinkDto.setScheduleId(schedulePo.getScheduleId());
                loanRepayLinkDto.setCapital(repayDtlPo.getRepayPrinAmt());
                loanRepayLinkDto.setInterest(repayDtlPo.getRepayIntr());
                loanRepayLinkDto.setFine(repayDtlPo.getRepayFine());
                innerLinkRepayService.add(loanRepayLinkDto);

            }


            loanDtlPo.setRsrvAmt(total);

            loanDtlDao.save(loanDtlPo);
        }

    }

    @Override
    @Transactional
    public void externalRepayCallback(ExternalRepayCallbackDto externalRepayCallbackDto) throws BizServiceException {
        //数据检查以及获取基础对象
        LoanDtlPo loanDtlPo = this.checkAppl(externalRepayCallbackDto);

        //获得还款计划编号
        String scheduleId = this.getScheduleId(loanDtlPo.getLoanId(), externalRepayCallbackDto.getPeriod());

        //根据贷款编号和期号拿到处理中的还款记录
        RepayDtlDto process = this.getProcess(scheduleId);

        ERepayState state = this.state(
                externalRepayCallbackDto.getResult(),
                this.getWhole(externalRepayCallbackDto.getRefundFlag())
        );

        //没找到所需处理的状态，直接返回，不做处理
        if (state == null) {
            return;
        }

        switch (state) {
            case SUCCESS:
                this.atzSuccess(scheduleId, loanDtlPo, externalRepayCallbackDto, process);
                break;
            case FAILED:
                this.atzFailed(scheduleId, loanDtlPo, externalRepayCallbackDto, process);
                break;
            default:
                break;
        }
    }

    @Override
    public void withhold(String userId, EPrType prType, RepayReqDto request) throws BizServiceException {

        LoanDtlDto loanDtlDto = innerLoanService.get(request.getLoanId());

        if (loanDtlDto.getDepository()) {
            this.depositoryWithhold(userId, prType, request, loanDtlDto);
        } else {
            this.cashierWithhold(userId, prType, request, loanDtlDto);
        }

    }

    private void cashierWithhold(String userId, EPrType prType, RepayReqDto request, LoanDtlDto loanDtlDto) throws BizServiceException {
        //生成还款预订单
        RepayDtlDto repayDtlDto = this.inAdvanceRepay(userId, request, prType);

        //发起还款
        String appId = AppConfigUtil.getConfig("cashier.pay.appId");
        String sellerId = AppConfigUtil.getConfig("cashier.pay.sellerId");
        String partnerId = AppConfigUtil.getConfig("cashier.pay.partnerId");
        String repayCallback = AppConfigUtil.getConfig("cashier.repay.callback.url");
        LoanPayDto withholdRequest = new LoanPayDto();
        withholdRequest.setAppId(appId);
        withholdRequest.setSellerId(sellerId);
        withholdRequest.setPartnerId(partnerId);
        withholdRequest.setBuyerId(loanDtlDto.getUserId());
        withholdRequest.setBankCardId(loanDtlDto.getBankCardId().toString());
        withholdRequest.setOutTradeNo(repayDtlDto.getRepayId());
        withholdRequest.setSrcAmt(NumberUtil.roundTwo(repayDtlDto.getTotal()).toString());
        withholdRequest.setNotifyUrl(repayCallback);
        if (request.getCouponIds() == null) {
            List<Long> tt = new ArrayList<>();
            request.setCouponIds(tt);
        }
        withholdRequest.setPassbackParams(JsonUtil.toJson(request));

        loanPayService.withhold(withholdRequest);
    }

    private void depositoryWithhold(String userId, EPrType prType, RepayReqDto request, LoanDtlDto loanDtlDto) throws BizServiceException {
        //生成还款预订单
        RepayDtlDto repayDtlDto = this.inAdvanceRepay(userId, request, prType);

        LoanDepositoryAcctDto loanDepositoryAcctDto = loanDepositoryAcctService.findByBankCardId(loanDtlDto.getBankCardId());

        RepayDto repayDto = new RepayDto();

        repayDto.setLoanId(repayDtlDto.getLoanId());
        repayDto.setAcctNo(loanDepositoryAcctDto.getAcctNo());
        repayDto.setRepayId(repayDtlDto.getRepayId());
        repayDto.setRepayCapital(repayDtlDto.getRepayPrinAmt());
        repayDto.setRepayInterest(
                repayDtlDto.getRepayIntr().add(repayDtlDto.getRepayFine()).add(repayDtlDto.getRepayFee())
        );

        if (request.getCouponIds() == null) {
            List<Long> tt = new ArrayList<>();
            request.setCouponIds(tt);
        }

        repayDto.setPassbackParams(JsonUtil.toJson(request));

        try {
            transactionService.repay(repayDto);
        } catch (Exception e) {
            LOGGER.error("存管还款发起失败", e);

            //强制性调用还款失败逻辑
            this.repayFailed(repayDtlDto);

            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "存管还款发起失败", e);
        }

    }

    /**
     * @param scheduleId
     * @param externalRepayCallbackDto
     * @return
     */
    private List<ScheduleDto> externalUpdateSchedule(LoanDtlDto loanDtlDto, String scheduleId, ExternalRepayCallbackDto externalRepayCallbackDto) {

        List<ScheduleDto> schedules = innerScheduleService.getLoanRepayList(loanDtlDto.getLoanId());

        if (externalRepayCallbackDto.getResult() == null || !externalRepayCallbackDto.getResult()) {
            return schedules;
        }

        if (CollectionUtils.isNotEmpty(schedules)) {
            for (ScheduleDto schedule : schedules) {
                if (!scheduleId.equals(schedule.getScheduleId())) {
                    continue;
                }

                schedule.setActualCapital(schedule.getActualCapital().add(externalRepayCallbackDto.getCapital()));
                schedule.setActualInterest(schedule.getActualInterest().add(externalRepayCallbackDto.getInterest()));
                schedule.setActualFineCapital(schedule.getActualFineCapital().add(externalRepayCallbackDto.getFine()));
                schedule.setActualFee(schedule.getActualFee().add(externalRepayCallbackDto.getFee()));
                schedule.setPayDate(DateHelper.formatDate(DateHelper.getDate(externalRepayCallbackDto.getDate(), DateHelper.SIMPLE_DATE_YMD)));

                if (externalRepayCallbackDto.getRefundFlag() == ERefundFlag.WHOLE) {
                    schedule.setScheduleStatus(EScheduleStatus.PAID);
                }


                innerScheduleService.save(schedule);
            }
        }

        return schedules;

    }

    private LoanDtlPo checkAppl(ExternalRepayCallbackDto externalRepayCallbackDto) {

        if (externalRepayCallbackDto == null || StringUtils.isEmpty(externalRepayCallbackDto.getApplId())) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "外部还款回调检测失败");
        }

        LoanApplPo loanApplPo = loanApplDao.findOne(externalRepayCallbackDto.getApplId());

        if (loanApplPo == null) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "外部还款回调检测失败");
        }

        LoanDtlPo loanDtlPo = loanDtlDao.findByApplId(loanApplPo.getApplId());
        if (loanDtlPo == null) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "外部还款回调检测失败");
        }

        return loanDtlPo;
    }

    private String getScheduleId(String loanId, Integer period) {
        return loanId + "_" + period;
    }

    private boolean canSendMessage(RepayDtlDto repay, String mobile) {
        return repay != null && repay.getTransMode() == ETransMode.AUTO && StringUtils.isNotEmpty(mobile);
    }

    @Override
    @Transactional
    public void repayFailed(RepayDtlDto repay) {
        LoanDtlDto loan = innerLoanService.get(repay.getLoanId());
        RepayDtlPo repayDtlPo = ConverterService.convert(repay, RepayDtlPo.class);

        BigDecimal capital = repayDtlPo.getRepayPrinAmt();

        //释放锁定金额
        loan.setRsrvAmt(AmtUtils.positive(loan.getRsrvAmt().subtract(capital)));

        repay.setStatus(ERepayStatus.FAILED);

        this.save(repay);
        innerLoanService.save(loan);

        PayRecvOrdDto payRecvOrdDto = innerPayRecvOrdService.findByBizId(repay.getRepayId());
        payRecvOrdDto.setOrdStatus(EOrdStatus.FAILED);
        innerPayRecvOrdService.save(payRecvOrdDto);
    }


    /**
     * 还款具体逻辑回调实现
     *
     * @param request
     * @param repay
     * @param payRecvOrdDto
     * @throws BizServiceException
     */
    private void repaySuccess(RepayReqDto request, RepayDtlDto repay, PayRecvOrdDto payRecvOrdDto) throws BizServiceException {
        LoanDtlDto loan = innerLoanService.get(repay.getLoanId());

        assertLoanNotPaid(loan);

        RepayDtlPo repayDtlPo = ConverterService.convert(repay, RepayDtlPo.class);

        BigDecimal capital = repayDtlPo.getRepayPrinAmt();

        LoanPreRepayMiddleDto middle = null;
        switch (request.getRepayType()) {
            case PRE_ALL:
            case PRE_CURRENT:
                middle = this.listPreRepayMiddle(request.getRepayType(), repayDtlPo, loan);
                break;
            case DAY:
                middle = this.listDayRepayMiddle(repayDtlPo, loan);
                break;
            case OVERDUE:
                middle = this.listOverdueRepayMiddle(repayDtlPo, loan);
                break;
            case PERIOD:
                middle = this.listPeriodRepayMiddle(request, repayDtlPo, loan);
            default:
                break;
        }

        if (middle == null) {
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "还款发生异常");
        }

        for (ScheduleDto schedule : middle.getSchedules()) {
            innerScheduleService.save(schedule);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("loanId:{}, repayId:{}, schedule:{}", loan.getLoanId(), repayDtlPo.getRepayId(), schedule);
            }
        }

        repay.setStatus(ERepayStatus.SUCCESS);
        this.save(repay);

        this.afterRepaySuccess(loan, capital, middle.getSchedules());
    }

    private void afterRepaySuccess(LoanDtlDto loan, BigDecimal capital, List<ScheduleDto> schedules) {
        LOGGER.info("start afterRepaySuccess, loan:{}, capital, schedules:{}", loan, capital, schedules);

        loan.setRepayedAmt(loan.getRepayedAmt().add(capital));
        if (loan.getRepayedAmt().compareTo(loan.getLoanAmt()) >= 0) {
            loan.setRepayedAmt(loan.getLoanAmt());
        }

        //释放锁定金额
        loan.setRsrvAmt(AmtUtils.positive(loan.getRsrvAmt().subtract(capital)));
        if (loan.getRsrvAmt().compareTo(BigDecimal.ZERO) < 0) {
            loan.setRsrvAmt(BigDecimal.ZERO);
        }

        //如果逾期状态已经被清，则首先更新贷款状态至正常
        if (this.hasNoOverdue(loan, schedules)) {
            loan.setLoanStat(ELoanStat.NORMAL);
        }

        boolean isBTesPaid = false;

        //还款金额已清
        if (loan.getRepayedAmt().compareTo(loan.getLoanAmt()) >= 0) {
            loan.setLoanStat(ELoanStat.PAID);
            if ("B".equals(loan.getTestSource())) {
                isBTesPaid = true;
            }
        }

        LOGGER.info("end afterRepaySuccess, loan:{}, schedules:{}", loan, schedules);

        innerLoanService.save(loan);

        //释放用户使用中的额度
        innerAcctReserveService.releaseCreditLine(loan.getAcctNo(), capital);

        if (isBTesPaid) {
            loanUserCreditLineService.back(loan.getUserId());
            loanUserCreditLineService.updateWithoutCheck(loan.getUserId());
        }
    }

    private LoanRepayRespDto completeCoupon(LoanRepayRespDto loanRepayRespDto, String repayId) {
        List<LoanCouponDto> coupons = loanCouponService.listByRepayId(repayId);
        if (CollectionUtils.isEmpty(coupons)) {
            return loanRepayRespDto;
        }
        LoanCouponDto coupon = coupons.get(0);
        if (coupon == null) {
            return loanRepayRespDto;
        }
        LoanCouponRepayDto loanCouponRepayDto = new LoanCouponRepayDto();

        //todo 认为目前的券都是免息券
        BigDecimal price = AmtUtils.min(loanRepayRespDto.getInterest(), coupon.getPrice());
        loanCouponRepayDto.setPrice(price);

        loanCouponRepayDto.setCouponType(coupon.getCouponType());

        loanRepayRespDto.setCoupon(loanCouponRepayDto);
        return loanRepayRespDto;
    }

    /**
     * 获取提前还款所需信息
     *
     * @param repayType
     * @param loan
     * @return
     */
    private LoanPreRepayMiddleDto listPreRepayMiddle(ERepayType repayType, RepayDtlPo repay, LoanDtlDto loan) {
        //获得当期还款计划
        ScheduleDto current = this.current(loan);

        List<ScheduleDto> schedules = null;
        List<ScheduleDto> rs = new ArrayList<>();
        List<LoanRepayLinkDto> links = new ArrayList<>();
        LoanPreRepayMiddleDto middle = new LoanPreRepayMiddleDto();

        //获得还款计划
        if (repayType == ERepayType.PRE_ALL) {
            schedules = innerScheduleService.getLoanRepayList(loan.getLoanId());
        } else if (repayType == ERepayType.PRE_CURRENT) {
            schedules = new ArrayList<>();
            schedules.add(current);
        }

        //违约金需要找到最近有效的一期还款计划进行挂靠
        BigDecimal fee = repay.getRepayFee();

        for (ScheduleDto schedule : schedules) {
            //往期自动忽略
            if (schedule.getSeqNo() < current.getSeqNo()) {
                continue;
            }

            //如果本期已清，也自动忽略
            if (schedule.getScheduleStatus() == EScheduleStatus.PAID) {
                continue;
            }

            //还款当期计息,往后不计
            BigDecimal interest = schedule.getSeqNo().equals(current.getSeqNo()) ?
                    current.getShouldInterest() : BigDecimal.ZERO;

            //保证违约金只会计单次
            BigDecimal myFee = BigDecimal.ZERO;
            if (fee.compareTo(BigDecimal.ZERO) > 0) {
                myFee = fee;
                fee = fee.subtract(myFee);
            }

            //还款计划状态变更
            schedule.setScheduleStatus(EScheduleStatus.PAID);
            schedule.setActualCapital(schedule.getShouldCapital());
            schedule.setActualInterest(interest);
            schedule.setActualFee(myFee);
            schedule.setPayDate(DateHelper.getWorkDate());

            rs.add(schedule);

            LoanRepayLinkDto link = new LoanRepayLinkDto();
            link.setCapital(schedule.getActualCapital());
            link.setInterest(interest);
            link.setFine(BigDecimal.ZERO);
            link.setRepayId(repay.getRepayId());
            link.setScheduleId(schedule.getScheduleId());

            links.add(link);
        }

        middle.setSchedules(rs);
        middle.setLinks(links);

        return middle;
    }

    /**
     * 获取随借随还所需信息
     *
     * @param repay
     * @param loan
     * @return
     */
    private LoanPreRepayMiddleDto listDayRepayMiddle(RepayDtlPo repay, LoanDtlDto loan) {

        LoanPreRepayMiddleDto middle = new LoanPreRepayMiddleDto();
        List<ScheduleDto> schedules = new ArrayList<>();
        List<LoanRepayLinkDto> links = new ArrayList<>();

        ScheduleDto schedule = this.getFirstSchedule(loan.getLoanId());

        if (schedule == null) {
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "还款计划获取异常");
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("listDayRepayMiddle,schedule:{},repay:{}", schedule, repay);
        }

        schedule.setActualCapital(schedule.getActualCapital().add(repay.getRepayPrinAmt()));
        schedule.setActualInterest(schedule.getActualInterest().add(repay.getRepayIntr()));
        schedule.setActualFee(BigDecimal.ZERO);
        schedule.setActualFineCapital(schedule.getActualFineCapital().add(repay.getRepayFine()));
        schedule.setActualFineInterest(BigDecimal.ZERO);
        schedule.setPayDate(DateHelper.getWorkDate());
        if (this.scheduleIsPaid(schedule)) {
            schedule.setScheduleStatus(EScheduleStatus.PAID);
        }
        schedules.add(schedule);

        LoanRepayLinkDto link = new LoanRepayLinkDto();
        link.setCapital(repay.getRepayPrinAmt());
        link.setInterest(repay.getRepayIntr());
        link.setFine(repay.getRepayFine());
        link.setRepayId(repay.getRepayId());
        link.setScheduleId(schedule.getScheduleId());
        links.add(link);

        middle.setSchedules(schedules);
        middle.setLinks(links);
        return middle;
    }

    /**
     * 手动代扣单期
     *
     * @param repay
     * @param loan
     */
    private LoanPreRepayMiddleDto listPeriodRepayMiddle(RepayReqDto request, RepayDtlPo repay, LoanDtlDto loan) {
        List<ScheduleDto> schedules = innerScheduleService.getLoanRepayList(loan.getLoanId());

        LoanPreRepayMiddleDto middle = new LoanPreRepayMiddleDto();
        List<LoanRepayLinkDto> links = new ArrayList<>();

        for (ScheduleDto schedule : schedules) {
            if (schedule.getSeqNo().equals(request.getPeriod())) {
                schedule.setActualFineCapital(schedule.getActualFineCapital().add(repay.getRepayFine()));
                schedule.setActualInterest(schedule.getActualInterest().add(repay.getRepayIntr()));
                schedule.setActualCapital(schedule.getActualCapital().add(repay.getRepayPrinAmt()));
                schedule.setPayDate(DateHelper.getWorkDate());
                if (this.scheduleIsPaid(schedule)) {
                    schedule.setScheduleStatus(EScheduleStatus.PAID);
                }

                LoanRepayLinkDto link = new LoanRepayLinkDto();
                link.setRepayId(repay.getRepayId());
                link.setScheduleId(schedule.getScheduleId());
                link.setCapital(repay.getRepayPrinAmt());
                link.setInterest(repay.getRepayIntr());
                link.setFine(repay.getRepayFine());
                links.add(link);
            }
        }

        middle.setSchedules(schedules);
        middle.setLinks(links);
        return middle;
    }

    /**
     * 等额本息逾期还款
     *
     * @param repay
     * @param loan
     */
    private LoanPreRepayMiddleDto listOverdueRepayMiddle(RepayDtlPo repay, LoanDtlDto loan) {
        List<ScheduleDto> schedules = innerScheduleService.getLoanRepayList(loan.getLoanId());
        BigDecimal total = repay.getRepayPrinAmt()
                .add(repay.getRepayIntr())
                .add(repay.getRepayFine())
                .add(repay.getRepayFee());
        List<OverdueMonthPreviewDto> overdues = innerLoanOverdueService.monthPreview(repay.getLoanId(), total, null);

        LoanPreRepayMiddleDto middle = new LoanPreRepayMiddleDto();
        List<ScheduleDto> rs = new ArrayList<>();
        List<LoanRepayLinkDto> links = new ArrayList<>();

        for (OverdueMonthPreviewDto overdue : overdues) {
            //如果逾期的逾期涉及金额变动的，做状态更新和金额计算
            if (this.changed(overdue)) {
                ScheduleDto schedule = this.index(schedules, overdue.getPeriod());
                if (schedule == null) {
                    continue;
                }
                schedule.setActualFineCapital(schedule.getActualFineCapital().add(overdue.getFine()));
                schedule.setActualInterest(schedule.getActualInterest().add(overdue.getInterest()));
                schedule.setActualCapital(schedule.getActualCapital().add(overdue.getCapital()));
                schedule.setPayDate(DateHelper.getWorkDate());

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("listOverdueRepayMiddle before update status,{}", schedule);
                }

                if (this.scheduleIsPaid(schedule)) {
                    schedule.setScheduleStatus(EScheduleStatus.PAID);
                }
                rs.add(schedule);

                LoanRepayLinkDto link = ConverterService.convert(overdue, LoanRepayLinkDto.class);
                link.setRepayId(repay.getRepayId());
                link.setScheduleId(schedule.getScheduleId());
                links.add(link);
            }
        }

        middle.setSchedules(rs);
        middle.setLinks(links);
        return middle;
    }

    /**
     * 是否已经款清
     *
     * @param schedule
     * @return
     */
    private boolean scheduleIsPaid(ScheduleDto schedule) {
        BigDecimal actual = NumberUtil.roundTwo(schedule.getActualCapital());
        BigDecimal should = NumberUtil.roundTwo(schedule.getShouldCapital());
        return actual.compareTo(should) >= 0;
    }

    private void assertLoanNotPaid(LoanDtlDto loan) {
        //已结清了,或者编号不存在,没必要进行还款
        if (null == loan || loan.getLoanStat() == ELoanStat.PAID) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "还款失败，贷款信息存在异常或已结清");
        }
    }

    private void assertAmtNotZero(RepayReqDto request) {
        if (Arrays.asList(ERepayType.PRE_ALL, ERepayType.PRE_CURRENT, ERepayType.PERIOD).contains(request.getRepayType())) {
            return;
        }
        if (request.getAmt() != null && request.getAmt().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizServiceException(EErrorCode.LOAN_CANT_ZERO, "can't repay zero amt");
        }
    }

    private void assertRepaying(RepayReqDto request) {
        List<RepayDtlPo> list = repayDao.getRepayedList(request.getLoanId());
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (RepayDtlPo repayDtlPo : list) {
            if (Arrays.asList(ERepayStatus.PROCESS, ERepayStatus.PAY).contains(repayDtlPo.getStatus())) {
                throw new BizServiceException(EErrorCode.LOAN_REPAY_EXCEED_QUOTA, "还款处理中，无法重新发起");
            }
        }
    }

    private void assertValidRepayAmt(LoanDtlPo loanDtlPo) {
        BigDecimal surplus = AmtUtils.positive(loanDtlPo.getLoanAmt().subtract(loanDtlPo.getRepayedAmt()));
        //还款占用金额大于应还金额，说明未支付的还款次数过多,不允许再发起
        if (loanDtlPo.getRsrvAmt().compareTo(surplus) > 0) {
            throw new BizServiceException(EErrorCode.LOAN_REPAY_EXCEED_QUOTA, "超过还款限额");
        }
    }

    /**
     * 是否已经不包含逾期还款计划
     *
     * @param schedules
     * @return
     */
    private Boolean hasNoOverdue(LoanDtlDto loan, List<ScheduleDto> schedules) {
        //如果schedules没有传递，则根据loanId去取最新的schedules
        if (CollectionUtils.isEmpty(schedules)) {
            schedules = innerScheduleService.getLoanRepayList(loan.getLoanId());
        }

        for (ScheduleDto schedule : schedules) {
            if (schedule.getScheduleStatus() == EScheduleStatus.OVERDUE) {
                return false;
            }
        }

        return true;
    }

    /**
     * 获取当期还款计划
     *
     * @param loan
     * @return
     */
    private ScheduleDto current(LoanDtlDto loan) {
        ScheduleDto current = null;
        try {
            current = innerScheduleService.getCurrentSchedule(loan.getLoanId());
            if (current == null) {
                throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "当期还款计划获取失败");
            }
        } catch (Exception e) {
            LOGGER.warn("exception", e);
        }

        return current;
    }

    /**
     * 获取第一期的还款计划
     *
     * @param loanId
     * @return
     */
    private ScheduleDto getFirstSchedule(String loanId) {
        List<ScheduleDto> schedules = innerScheduleService.list(loanId);

        if (CollectionUtils.isEmpty(schedules)) {
            return null;
        }

        for (ScheduleDto schedule : schedules) {
            if (schedule.getSeqNo().equals(1)) {
                return schedule;
            }
        }
        return null;
    }

    /**
     * 逾期期数是否涉及金额变动
     *
     * @param overdue
     * @return
     */
    private Boolean changed(OverdueMonthPreviewDto overdue) {
        return overdue.getCapital().compareTo(BigDecimal.ZERO) > 0
                || overdue.getInterest().compareTo(BigDecimal.ZERO) > 0
                || overdue.getFine().compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 根据期数返回还款计划
     *
     * @param schedules
     * @param seqNo
     * @return
     */
    private ScheduleDto index(List<ScheduleDto> schedules, Integer seqNo) {
        for (ScheduleDto schedule : schedules) {
            if (seqNo.equals(schedule.getSeqNo())) {
                return schedule;
            }
        }

        return null;
    }

    private List<SchedulePo> getOverdueList(String nowDate, int currentPage, int size) {

        Specification<SchedulePo> spec = (Root<SchedulePo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.equal(root.<SchedulePo>get("scheduleStatus"), EScheduleStatus.NOTYET));
            expressions.add(cb.lessThan(root.<SchedulePo>get("dueDate").as(String.class), nowDate));
            return predicate;
        };

        Pageable pageable = new PageRequest(currentPage - 1, size);
        Page<SchedulePo> page = scheduleDao.findAll(spec, pageable);

        return page.getContent();
    }

    private List<RepayDtlPo> getRepayList(int currentPage, int size) {
        Specification<RepayDtlPo> spec = (Root<RepayDtlPo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            expressions.add(cb.lessThanOrEqualTo(root.get("createTs"), new Date()));
            return predicate;
        };

        Pageable pageable = new PageRequest(currentPage - 1, size);
        Page<RepayDtlPo> page = repayDao.findAll(spec, pageable);
        return page.getContent();
    }

    private void assertRepayRequest(String userId, RepayReqDto request, LoanDtlDto loan) {
        if (StringUtils.isNotEmpty(userId) && !userId.equals(loan.getUserId())) {
            throw new BizServiceException(EErrorCode.LOAN_APPL_NOT_YOURS, "不是你自己的贷款单，无法操作");
        }

        if (null == loan) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "贷款单不存在，无法操作");
        }

        if (loan.getRepayMode() == ERepayMode.INTR_PER_DIEM && request.getRepayType() != ERepayType.DAY) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "还款状态匹配错误，无法发起还款");
        }

        if (loan.getRepayMode() == ERepayMode.MONTH_AVE_CAP_PLUS_INTR && request.getRepayType() == ERepayType.DAY) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "还款状态匹配错误，无法发起还款");
        }

        //等额本息逾期还款、随借随还还款，需要对传入的还款金额，进行最大可还金额的判定
        if (Arrays.asList(ERepayType.DAY, ERepayType.OVERDUE).contains(request.getRepayType())) {
            BigDecimal all = loanOverdueService.all(request.getLoanId());
            all = NumberUtil.roundTwo(all);
            if (request.getAmt() != null && request.getAmt().compareTo(all) > 0) {
                throw new BizServiceException(EErrorCode.LOAN_REPAY_CALC_EXCEED_MAX, "超过最大可还金额");
            }
        }

    }

    /**
     * 保存还款记录
     *
     * @param loan
     * @param calc
     */
    private RepayDtlPo saveRepayDtl(LoanDtlDto loan, LoanCalcDto calc, EPrType prType, RepayReqDto request) {
        RepayDtlPo repayDtlPo = new RepayDtlPo();
        repayDtlPo.setRepayPrinAmt(calc.getCapital());
        repayDtlPo.setRepayIntr(calc.getInterest());
        repayDtlPo.setRepayFine(calc.getFine());
        repayDtlPo.setRepayFee(calc.getFee());
        repayDtlPo.setLoanId(loan.getLoanId());
        repayDtlPo.setAcctNo(loan.getAcctNo());
        repayDtlPo.setSurplusCapital(AmtUtils.positive(loan.getLoanAmt().subtract(loan.getRepayedAmt())));
        repayDtlPo.setStatus(ERepayStatus.PROCESS);
        repayDtlPo.setRepayDate(DateHelper.getWorkDate());
        repayDtlPo.setRepayDateTime(new Date());
        if (prType == EPrType.CASHIER_RECEIVE) {
            repayDtlPo.setTransMode(ETransMode.COUNTER);
            //收银台初始状态为待支付
            repayDtlPo.setStatus(ERepayStatus.PAY);
        } else if (prType == EPrType.RECEIVE) {
            repayDtlPo.setTransMode(ETransMode.AUTO);
            repayDtlPo.setStatus(ERepayStatus.PROCESS);
        } else if (prType == EPrType.DEPOSITOR_RECEIVE) {
            repayDtlPo.setTransMode(ETransMode.AUTO);
            repayDtlPo.setStatus(ERepayStatus.PROCESS);
        }
        repayDtlPo.setRepayType(request.getRepayType());
        repayDtlPo = repayDao.save(repayDtlPo);
        return repayDtlPo;
    }

    /**
     * 初始化收付指令
     *
     * @param repayDtlPo
     */
    private PayRecvOrdPo savePayRecv(LoanDtlDto loan, RepayDtlPo repayDtlPo, EPrType prType, LoanCalcDto calc) {
        PayRecvOrdPo payRecvOrdPo = new PayRecvOrdPo();
        payRecvOrdPo.setPrType(prType);
        payRecvOrdPo.setOrdStatus(EOrdStatus.PROCESS);
        payRecvOrdPo.setUserId(loan.getUserId());
        payRecvOrdPo.setAcctNo(loan.getAcctNo());
        payRecvOrdPo.setTrxAmt(NumberUtil.roundTwo(calc.getPromoTotal()));
        payRecvOrdPo.setTrxDate(DateHelper.getWorkDate());
        payRecvOrdPo.setBizId(repayDtlPo.getRepayId());
        payRecvOrdPo = payRecvOrdDao.save(payRecvOrdPo);
        return payRecvOrdPo;
    }

    /**
     * 还款金额计算方法
     *
     * @param userId
     * @param request
     * @return
     */
    public LoanCalcDto calc(String userId, RepayReqDto request) throws BizServiceException {
        LoanCalcDto loanCalcDto = null;
        List<OverdueMonthPreviewDto> overdues = new ArrayList<>();

        assertAmtNotZero(request);

        if (request.getAmt() != null) {
            request.setAmt(NumberUtil.roundTwo(request.getAmt()));
        }

        switch (request.getRepayType()) {
            case PRE_ALL:
                loanCalcDto = loanCalcService.prepayAll(userId, request.getLoanId());
                break;
            case PERIOD:
                loanCalcDto = loanCalcService.period(userId, request.getLoanId(), request.getPeriod(), request.getForceDate());
                break;
            case PRE_CURRENT:
                loanCalcDto = loanCalcService.prepayCurrent(userId, request.getLoanId());
                break;
            case DAY:
                loanCalcDto = loanCalcService.any(userId, request.getLoanId(), request.getAmt(), request.getForceDate());
                break;
            case OVERDUE:
                overdues = loanOverdueService.monthPreview(request.getLoanId(), request.getAmt(), null);
                break;
            case AVE_CAP_AVG_INTR_REPAY:
                loanCalcDto = loanAvgCapAvgIntrCalcService.repay(userId,request.getLoanId());
                break;
            case AVE_CAP_AVG_INTR_REPAY_ALL:
                loanCalcDto = loanAvgCapAvgIntrCalcService.repayAll(userId,request.getLoanId());
                break;
        }

        if (overdues.size() > 0) {
            loanCalcDto = new LoanCalcDto();
            for (OverdueMonthPreviewDto overdue : overdues) {
                loanCalcDto.setCapital(loanCalcDto.getCapital().add(overdue.getCapital()));
                loanCalcDto.setInterest(loanCalcDto.getInterest().add(overdue.getInterest()));
                loanCalcDto.setFine(loanCalcDto.getFine().add(overdue.getFine()));
                loanCalcDto.setFee(loanCalcDto.getFee().add(loanCalcDto.getFee()));
            }
        }

        //保存原始总额
        loanCalcDto.setTotal(
                loanCalcDto.getCapital()
                        .add(loanCalcDto.getInterest())
                        .add(loanCalcDto.getFine())
                        .add(loanCalcDto.getFee())
        );

        loanCalcDto.setPromoTotal(loanCalcDto.getTotal());

        if (loanCalcDto != null) {
            loanCalcDto = this.formatLoanCalcDto(loanCalcDto);
        }

        return loanCalcDto;
    }

    private PayRecvOrdDto convertCashierCallback(PayRecvOrdDto payRecvOrdDto, LoanCashierCallbackDto callback) {
        if (callback == null) {
            return payRecvOrdDto;
        }
        if (StringUtils.isEmpty(callback.getDesc())) {
            LOGGER.error("callback desc is null");
            return payRecvOrdDto;
        }
        Map<String, EOrdStatus> statusMap = new HashMap<>();
        statusMap.put("PROCESS", EOrdStatus.PROCESS);
        statusMap.put("SUCCESS", EOrdStatus.SUCCESS);
        statusMap.put("FAILED", EOrdStatus.FAILED);
        EOrdStatus status = statusMap.get(callback.getDesc());

        payRecvOrdDto.setOrdStatus(status != null ? status : EOrdStatus.PROCESS);
        payRecvOrdDto.setRetCode(callback.getRetCode());
        payRecvOrdDto.setRetMsg(callback.getRetMsg());
        payRecvOrdDto.setRetChannelCode(callback.getRetChannelCode());
        payRecvOrdDto.setRetChannelName(callback.getRetChannelName());

        return payRecvOrdDto;
    }

    /**
     * 格式化计算结果
     *
     * @param loanCalcDto
     */
    private LoanCalcDto formatLoanCalcDto(LoanCalcDto loanCalcDto) {
        loanCalcDto.setCapital(NumberUtil.roundTwo(loanCalcDto.getCapital()));
        loanCalcDto.setInterest(NumberUtil.roundTwo(loanCalcDto.getInterest()));
        loanCalcDto.setTotal(NumberUtil.roundTwo(loanCalcDto.getTotal()));
        loanCalcDto.setPromoTotal(NumberUtil.roundTwo(loanCalcDto.getPromoTotal()));
        loanCalcDto.setFine(NumberUtil.roundTwo(loanCalcDto.getFine()));
        loanCalcDto.setFee(NumberUtil.roundTwo(loanCalcDto.getFee()));
        loanCalcDto.setSurplus(NumberUtil.roundTwo(loanCalcDto.getSurplus()));
        return loanCalcDto;
    }

    private void atzSuccess(String scheduleId, LoanDtlPo loanDtlPo, ExternalRepayCallbackDto callback, RepayDtlDto process) {
        this.atzRepayCallback(scheduleId, loanDtlPo, ERepayStatus.SUCCESS, callback, process);

        LoanDtlDto loanDtlDto = ConverterService.convert(loanDtlPo, LoanDtlDto.class);

        //获得最新还款计划
        List<ScheduleDto> schedules = this.externalUpdateSchedule(loanDtlDto, scheduleId, callback);

        //根据还款计划刷新贷款记录以及用户额度
        this.afterRepaySuccess(loanDtlDto, callback.getCapital(), schedules);
    }

    private RepayDtlPo atzRepayDtl(LoanDtlPo loanDtlPo, ERepayStatus repayStatus, ExternalRepayCallbackDto externalRepayCallbackDto, RepayDtlDto process) {

        RepayDtlPo repayDtlPo;
        if (process == null) {
            //增加还款记录
            repayDtlPo = new RepayDtlPo();
            repayDtlPo.setAcctNo(loanDtlPo.getAcctNo());
            repayDtlPo.setLoanId(loanDtlPo.getLoanId());
            repayDtlPo.setRepayType(ERepayType.PERIOD);
        } else {
            repayDtlPo = ConverterService.convert(process, RepayDtlPo.class);
        }

        repayDtlPo.setStatus(repayStatus);

        if (externalRepayCallbackDto.getCapital() != null) {
            repayDtlPo.setRepayPrinAmt(externalRepayCallbackDto.getCapital());
        }
        if (externalRepayCallbackDto.getInterest() != null) {
            repayDtlPo.setRepayIntr(externalRepayCallbackDto.getInterest());
        }
        if (externalRepayCallbackDto.getFine() != null) {
            repayDtlPo.setRepayFine(externalRepayCallbackDto.getFine());
        }
        if (externalRepayCallbackDto.getFee() != null) {
            repayDtlPo.setRepayFee(externalRepayCallbackDto.getFee());
        }

        repayDtlPo.setRepayDateTime(new Date());
        repayDtlPo.setRepayDate(DateHelper.formatDate(repayDtlPo.getRepayDateTime(), ApplicationConstant.DATE_FORMAT));
        repayDtlPo.setTransMode(ETransMode.AUTO);

        //避免值拷贝出现的乐观锁冲突问题
        RepayDtlDto repayDtlDto = innerRepayService.save(ConverterService.convert(repayDtlPo, RepayDtlDto.class));
        return ConverterService.convert(repayDtlDto, RepayDtlPo.class);
    }

    private void atzPayRecv(LoanDtlPo loanDtlPo, RepayDtlPo repayDtlPo, ExternalRepayCallbackDto callback) {

        EOrdStatus ordStatus = EOrdStatus.PROCESS;

        switch (repayDtlPo.getStatus()) {
            case PAY:
            case PROCESS:
                ordStatus = EOrdStatus.PROCESS;
                break;
            case SUCCESS:
                ordStatus = EOrdStatus.SUCCESS;
                break;
            case FAILED:
                ordStatus = EOrdStatus.FAILED;
                break;
            default:
                break;
        }


        List<PayRecvOrdPo> payRecvOrdPos = payRecvOrdDao.findByBizId(repayDtlPo.getRepayId());

        PayRecvOrdPo payRecvOrdPo;
        if (payRecvOrdPos.size() == 0) {
            payRecvOrdPo = new PayRecvOrdPo();
            payRecvOrdPo.setBizId(repayDtlPo.getRepayId());
            payRecvOrdPo.setPrType(EPrType.RECEIVE);
            payRecvOrdPo.setAcctNo(loanDtlPo.getAcctNo());
        } else {
            payRecvOrdPo = payRecvOrdPos.get(0);
        }

        payRecvOrdPo.setOrdStatus(ordStatus);
        payRecvOrdPo.setRetMsg(callback.getReason());
        payRecvOrdPo.setTrxDate(repayDtlPo.getRepayDate());
        if (callback.getAmount() != null) {
            payRecvOrdPo.setTrxAmt(callback.getAmount());
        }

        payRecvOrdDao.save(payRecvOrdPo);
    }

    private void atzLink(String scheduleId, RepayDtlPo repayDtlPo, RepayDtlDto process) {

        LoanRepayLinkDto loanRepayLinkDto = new LoanRepayLinkDto();

        loanRepayLinkDto.setRepayId(repayDtlPo.getRepayId());
        loanRepayLinkDto.setScheduleId(scheduleId);
        loanRepayLinkDto.setCapital(repayDtlPo.getRepayPrinAmt());
        loanRepayLinkDto.setInterest(repayDtlPo.getRepayIntr());
        loanRepayLinkDto.setFine(repayDtlPo.getRepayFine());

        //本来处理中的记录就是根据link找到来的，如果存在process，link一定已经存在，刷新link金额
        if (process != null) {
            LinkRepayDto linkRepayDto = innerLinkRepayService.findByRepayIdAndScheduleId(process.getRepayId(), scheduleId);
            if (linkRepayDto != null) {
                linkRepayDto.setCapital(repayDtlPo.getRepayPrinAmt());
                linkRepayDto.setInterest(repayDtlPo.getRepayIntr());
                linkRepayDto.setFine(repayDtlPo.getRepayFine());
                innerLinkRepayService.save(linkRepayDto);
            }
        } else {
            innerLinkRepayService.add(loanRepayLinkDto);
        }

    }

    private void atzFailed(String scheduleId, LoanDtlPo loanDtlPo, ExternalRepayCallbackDto callback, RepayDtlDto process) {
        //爱投资失败返回是不会有金额明细信息的，为避免还款记录库中为NULL，将amount金额调配到本金部分，其他部分留0
        if (callback.getAmount() != null) {
            callback.setCapital(callback.getAmount());
            callback.setInterest(BigDecimal.ZERO);
            callback.setFine(BigDecimal.ZERO);
            callback.setFee(BigDecimal.ZERO);
        }

        this.atzRepayCallback(scheduleId, loanDtlPo, ERepayStatus.FAILED, callback, process);

        this.atzFailedOther(scheduleId, loanDtlPo);
    }

    private void atzFailedOther(String scheduleId, LoanDtlPo loanDtlPo) {

        //数据校验
        if (StringUtils.isEmpty(scheduleId) || loanDtlPo == null || StringUtils.isEmpty(loanDtlPo.getLoanId())) {
            return;
        }

        //假如1-12期的还款回调，第2期发起失败后，3-12期就不会存在回调，需要将3-12期处理中的记录全部变更为失败

        //默认搜出来的方法保证相对有序
        List<SchedulePo> schedules = scheduleDao.findByLoanId(loanDtlPo.getLoanId());

        if (CollectionUtils.isEmpty(schedules)) {
            return;
        }

        //根据scheduleId找到它之后的还款计划标记
        boolean begin = false;
        Set<String> scheduleIds = new HashSet<>();
        for (SchedulePo schedule : schedules) {

            if (begin) {
                scheduleIds.add(schedule.getScheduleId());
            }

            if (scheduleId.equals(schedule.getScheduleId())) {
                begin = true;
            }
        }

        this.batchFailed(scheduleIds);
    }

    private void batchFailed(Set<String> scheduleIds) {

        if (CollectionUtils.isEmpty(scheduleIds)) {
            return;
        }

        //找到所有处理中的还款记录
        List<RepayDtlPo> processes = this.getProcesses(scheduleIds, true);

        if (CollectionUtils.isEmpty(processes)) {
            return;
        }

        String loanId = processes.get(0).getLoanId();

        LoanDtlDto loan = innerLoanService.get(loanId);

        for (RepayDtlPo process : processes) {
            process.setStatus(ERepayStatus.FAILED);
            process.setRepayDateTime(new Date());
            process.setRepayDate(DateHelper.formatDate(process.getRepayDateTime(), ApplicationConstant.DATE_FORMAT));

            repayDao.save(process);

            List<PayRecvOrdPo> payRecvOrdPos = payRecvOrdDao.findByBizId(process.getRepayId());

            PayRecvOrdPo payRecvOrdPo = null;

            if (CollectionUtils.isNotEmpty(payRecvOrdPos)) {
                payRecvOrdPo = payRecvOrdPos.get(0);
            }

            if (payRecvOrdPo == null) {
                continue;
            }

            payRecvOrdPo.setOrdStatus(EOrdStatus.FAILED);
            payRecvOrdPo.setRetMsg("爱投资还款失败，当期自动失败");
            payRecvOrdPo.setTrxDate(process.getRepayDate());
            payRecvOrdDao.save(payRecvOrdPo);
        }

        //还款全部失败，强制性清空占用金额
        loan.setRsrvAmt(BigDecimal.ZERO);
        innerLoanService.save(loan);
    }

    private List<RepayDtlPo> getProcesses(Set<String> ids, boolean isScheduleId) {
        Set<String> repayIds = new HashSet<>();
        List<RepayDtlPo> processes = new ArrayList<>();

        if (isScheduleId) {
            List<LinkRepayDto> links = innerLinkRepayService.findByScheduleIds(ids);

            if (CollectionUtils.isEmpty(links)) {
                return processes;
            }

            for (LinkRepayDto link : links) {
                repayIds.add(link.getRepayId());
            }
        } else {
            repayIds = ids;
        }

        List<RepayDtlPo> repays = repayDao.findByRepayIdIn(repayIds);

        if (CollectionUtils.isEmpty(repays)) {
            return processes;
        }

        for (RepayDtlPo repay : repays) {
            if (repay.getStatus() == ERepayStatus.PROCESS) {
                processes.add(repay);
            }
        }

        return processes;

    }

    private void atzRepayCallback(String scheduleId, LoanDtlPo loanDtlPo, ERepayStatus repayStatus, ExternalRepayCallbackDto callback, RepayDtlDto process) {
        //新增或对还款记录做变更
        RepayDtlPo repayDtlPo = this.atzRepayDtl(loanDtlPo, repayStatus, callback, process);

        //根据还款记录新增或变更流水
        this.atzPayRecv(loanDtlPo, repayDtlPo, callback);

        //根据有没有处理中的还款记录决定是否要新增关联信息
        this.atzLink(scheduleId, repayDtlPo, process);
    }

    private ERepayState state(Boolean result, Boolean isWhole) {
        //非法，直接不做处理
        if (result == null) {
            return null;
        }
        if (result) {
            //非法，不做处理
            if (isWhole == null) {
                return null;
            }
            //当期结清
            if (isWhole) {
                //刷新金额，变更成功状态
                return ERepayState.SUCCESS;
            }
            //拆单了，未结清
            if (!isWhole) {
                //刷新金额，变更成功状态
                return ERepayState.SUCCESS;
            }
        } else {
            //还款失败逻辑处理，以失败amount金额更新本金金额，标记失败状态,找到之后的所有期，全部标记失败
            return ERepayState.FAILED;
        }
        return null;
    }

    private Boolean getWhole(ERefundFlag refundFlag) {

        Boolean isWhole = null;

        if (refundFlag != null) {
            switch (refundFlag) {
                case WHOLE:
                    isWhole = true;
                    break;
                case PART:
                    isWhole = false;
                    break;
            }
        }

        return isWhole;
    }

    private RepayDtlDto getProcess(String scheduleId) {

        //检查关联性
        List<LinkRepayDto> linkRepayDtos = innerLinkRepayService.findByScheduleId(scheduleId);

        /*
            检查关联还款单状态，判定是否存在处理中的还款记录
            始终认定外部还款回调还款记录和还款计划始终为1对1
         */
        RepayDtlDto targetRepayDtlDto = null;

        if (CollectionUtils.isNotEmpty(linkRepayDtos)) {
            for (LinkRepayDto linkRepayDto : linkRepayDtos) {
                RepayDtlDto repayDtlDto = this.get(linkRepayDto.getRepayId());
                if (repayDtlDto != null && ERepayStatus.PROCESS.equals(repayDtlDto.getStatus())) {
                    targetRepayDtlDto = repayDtlDto;
                }
            }
        }

        return targetRepayDtlDto;
    }

}

/**
 * 爱投资还款状态
 */
enum ERepayState {
    SUCCESS, FAILED
}