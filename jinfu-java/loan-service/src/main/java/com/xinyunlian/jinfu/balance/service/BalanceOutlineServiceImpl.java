package com.xinyunlian.jinfu.balance.service;

import com.xinyunlian.jinfu.balance.dao.BalanceCashierDao;
import com.xinyunlian.jinfu.balance.dao.BalanceDetailDao;
import com.xinyunlian.jinfu.balance.dao.BalanceOutlineDao;
import com.xinyunlian.jinfu.balance.dto.BalanceOutlineDto;
import com.xinyunlian.jinfu.balance.entity.BalanceDetailPo;
import com.xinyunlian.jinfu.balance.entity.BalanceOutlinePo;
import com.xinyunlian.jinfu.balance.enums.EBalanceOutlineStatus;
import com.xinyunlian.jinfu.balance.enums.EBalanceStatus;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.entity.LoanDtlPo;
import com.xinyunlian.jinfu.loan.entity.RepayDtlPo;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.pay.dao.PayRecvOrdDao;
import com.xinyunlian.jinfu.pay.entity.PayRecvOrdPo;
import com.xinyunlian.jinfu.repay.dao.RepayDao;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Willwang on 2017/5/22.
 */
@Component
public class BalanceOutlineServiceImpl implements BalanceOutlineService {

    @Autowired
    private RepayDao repayDao;

    @Autowired
    private BalanceOutlineDao balanceOutlineDao;

    @Autowired
    private BalanceDetailDao balanceDetailDao;

    @Autowired
    private PayRecvOrdDao payRecvOrdDao;

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private FinanceSourceService financeSourceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BalanceOutlineServiceImpl.class);

    @Override
    @Transactional
    public void generate() {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("balance generate start");
        }
        String  yesterday =  DateHelper.getYesterday();
        Date start = DateHelper.getStartDate(yesterday);
        Date end = DateHelper.getEndDate(yesterday);

        List<RepayDtlPo> repayDtlPos = repayDao.findByRepayDate(start, end);

        BalanceOutlinePo balanceOutlinePo = this.generateOutline(repayDtlPos);

        if(balanceOutlinePo == null){
            return;
        }

        BigDecimal total = this.generateDetail(balanceOutlinePo.getId(), repayDtlPos);

        //刷新单日实际还款总金额,不包含优惠券
        BalanceOutlinePo t = balanceOutlineDao.findByGenerateDate(new Date());
        t.setRepayAmt(total);
        balanceOutlineDao.save(t);

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("balance generate end");
        }
    }

    @Override
    @Transactional
    public void finish(String mgtUserId, Long outlineId) throws BizServiceException {
        Date now = new Date();
        List<BalanceDetailPo> balanceDetailPos = balanceDetailDao.findByOutlineId(outlineId);

        for (BalanceDetailPo balanceDetailPo : balanceDetailPos) {
            if(EBalanceStatus.NOT.equals(balanceDetailPo.getBalanceStatus())){
                throw new BizServiceException(EErrorCode.LOAN_CANT_FINISH_BALANCE, "当前对账未完成，无法结束");
            }
        }

        balanceOutlineDao.finish(EBalanceOutlineStatus.ALREADY.getCode(), now, mgtUserId, outlineId);
    }

    @Override
    @Transactional
    public void updateAutoed(Long outlineId, boolean autoed) throws BizServiceException {
        balanceOutlineDao.updateAutoedById(autoed, outlineId);
    }

    @Override
    public List<BalanceOutlineDto> listByDate(String start, String end) {
        List<BalanceOutlinePo> list = balanceOutlineDao.listByDate(DateHelper.getStartDate(start), DateHelper.getEndDate(end));
        List<BalanceOutlineDto> rs =  ConverterService.convertToList(list, BalanceOutlineDto.class);
        String partnerId = AppConfigUtil.getConfig("cashier.pay.partnerId");
        rs.forEach(item -> {
            item.setPartnerId(partnerId);

            if(item.getGenerateDate() != null){
                item.setGenerateDateStr(DateHelper.formatDate(item.getGenerateDate()));
            }

            if(item.getBalanceDate() != null){
                item.setBalanceDateStr(DateHelper.formatDate(item.getBalanceDate()));
            }
        });

        return rs;
    }

    /**
     * 生成对账概要
     * @param repayDtlPos
     * @return
     */
    private BalanceOutlinePo generateOutline(List<RepayDtlPo> repayDtlPos) {

        Date now = new Date();
        if(balanceOutlineDao.findByGenerateDate(now) != null){
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("当日已存在对账概要，无法重复生成");
            }
            return null;
        }


        List<FinanceSourceDto> financeSourceDtos = financeSourceService.getAll();
        Map<Integer, FinanceSourceDto> map = new HashMap<>();
        if(financeSourceDtos.size() > 0){
            financeSourceDtos.forEach(financeSourceDto -> map.put(financeSourceDto.getId(), financeSourceDto));
        }

        int repayCount = 0;
        for (RepayDtlPo repayDtlPo : repayDtlPos) {
            if(!this.isSuccessRepay(repayDtlPo)){
                continue;
            }
            if(!this.isOwnLoan(repayDtlPo, map)){
                continue;
            }
            repayCount++;
        }

        BalanceOutlinePo balanceOutlinePo = new BalanceOutlinePo();
        balanceOutlinePo.setBalanceOutlineStatus(EBalanceOutlineStatus.NOT);
        balanceOutlinePo.setGenerateDate(now);
        balanceOutlinePo.setRepayCount(repayCount);
        balanceOutlinePo.setAutoed(false);

        balanceOutlinePo = balanceOutlineDao.save(balanceOutlinePo);

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("balance outline generate success, date:{},outline_id:{}", DateHelper.getWorkDate(), balanceOutlinePo.getId());
        }

        return balanceOutlinePo;

    }

    /**
     * 生成对账详情
     * @return 还款总金额
     */
    private BigDecimal generateDetail(Long outlineId, List<RepayDtlPo> repayDtlPos){

        BigDecimal total = BigDecimal.ZERO;

        List<FinanceSourceDto> financeSourceDtos = financeSourceService.getAll();
        Map<Integer, FinanceSourceDto> map = new HashMap<>();
        if(financeSourceDtos.size() > 0){
            financeSourceDtos.forEach(financeSourceDto -> map.put(financeSourceDto.getId(), financeSourceDto));
        }

        for (RepayDtlPo repayDtlPo : repayDtlPos) {
            if(!this.isSuccessRepay(repayDtlPo)){
                continue;
            }

            if(!this.isOwnLoan(repayDtlPo, map)){
                continue;
            }

            BalanceDetailPo balanceDetailPo = new BalanceDetailPo();

            balanceDetailPo.setOutlineId(outlineId);
            balanceDetailPo.setRepayAmt(BigDecimal.ZERO);
            balanceDetailPo.setRepayDate(repayDtlPo.getRepayDateTime());
            balanceDetailPo.setRepayStatus(repayDtlPo.getStatus());
            balanceDetailPo.setRepayId(repayDtlPo.getRepayId());
            balanceDetailPo.setRepayDate(repayDtlPo.getRepayDateTime());
            balanceDetailPo.setBalanceStatus(EBalanceStatus.NOT);

            //贷款信息补充
            LoanDtlPo loanDtlPo = loanDtlDao.findOne(repayDtlPo.getLoanId());
            balanceDetailPo.setLoanId(loanDtlPo.getLoanId());
            balanceDetailPo.setProdName(loanDtlPo.getLoanName());
            balanceDetailPo.setUserId(loanDtlPo.getUserId());

            //通道信息补充
            List<PayRecvOrdPo> payRecvOrdPos = payRecvOrdDao.findByBizId(repayDtlPo.getRepayId());
            PayRecvOrdPo payRecvOrdPo = payRecvOrdPos.size() > 0 ? payRecvOrdPos.get(0) : null;
            if(payRecvOrdPo != null){
                balanceDetailPo.setChannelName(payRecvOrdPo.getRetChannelName());
                balanceDetailPo.setRepayAmt(payRecvOrdPo.getTrxAmt());
                total = total.add(payRecvOrdPo.getTrxAmt());
            }

            balanceDetailPo = balanceDetailDao.save(balanceDetailPo);

            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("balance detail success,balanceDetailPo:{}", balanceDetailPo);
            }
        }

        return total;
    }


    private boolean isSuccessRepay(RepayDtlPo repayDtlPo){
        return ERepayStatus.SUCCESS.equals(repayDtlPo.getStatus());
    }

    private boolean isOwnLoan(RepayDtlPo repayDtlPo, Map<Integer, FinanceSourceDto> financeSourceDtoMap){
        LoanDtlPo loanDtlPo = loanDtlDao.findOne(repayDtlPo.getLoanId());

        Integer financeSourceId = loanDtlPo.getFinanceSourceId() == null ? 1 : loanDtlPo.getFinanceSourceId();

        FinanceSourceDto financeSourceDto = financeSourceDtoMap.get(financeSourceId);

        return financeSourceDto != null && financeSourceDto.getType() == EFinanceSourceType.OWN;
    }

}
