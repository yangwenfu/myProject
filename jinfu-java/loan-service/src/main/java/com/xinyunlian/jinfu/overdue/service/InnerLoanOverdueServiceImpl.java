package com.xinyunlian.jinfu.overdue.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.common.util.LoanUtils;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.service.InnerApplService;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthDetailDto;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthPreviewDto;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import com.xinyunlian.jinfu.schedule.service.InnerScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Willwang on 2017/1/11.
 */
@Service
public class InnerLoanOverdueServiceImpl implements InnerLoanOverdueService{

    @Autowired
    private InnerApplService innerApplService;

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private InnerScheduleService innerScheduleService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InnerLoanOverdueServiceImpl.class);

    @Override
    public List<OverdueMonthDetailDto> month(String loanId) throws BizServiceException {
        List<OverdueMonthDetailDto> list = new ArrayList<>();

        Object[] rs = this.checkAndGet(loanId, ERepayMode.MONTH_AVE_CAP_PLUS_INTR);
        LoanProductDetailDto product = (LoanProductDetailDto) rs[1];
        LoanDtlDto loan = (LoanDtlDto) rs[0];
        List<ScheduleDto> schedules = innerScheduleService.list(loanId);

        BigDecimal surplusCapital = AmtUtils.positive(loan.getLoanAmt().subtract(loan.getRepayedAmt()));

        for (ScheduleDto schedule : schedules) {
            if(schedule.getScheduleStatus() == EScheduleStatus.OVERDUE){
                int fineDays = LoanUtils.getFineDays(schedule.getDueDate());

                OverdueMonthDetailDto detail = new OverdueMonthDetailDto();
                BigDecimal shouldCapital = AmtUtils.positive(schedule.getShouldCapital().subtract(schedule.getActualCapital()));
                BigDecimal shouldInterest = AmtUtils.positive(schedule.getShouldInterest().subtract(schedule.getActualInterest()));
                BigDecimal shouldFine = product.getFineType().getFine(surplusCapital, fineDays, product.getFineValue());

                detail.setDays(fineDays);
                detail.setPeriod(schedule.getSeqNo());
                detail.setTotalPeriod(schedules.size());
                detail.setRepayDate(schedule.getDueDate());
                detail.setCapital(shouldCapital);
                detail.setInterest(shouldInterest);
                detail.setFine(shouldFine);
                detail.setShould(shouldCapital.add(shouldInterest).add(shouldFine));
                list.add(detail);
            }
        }

        return list;
    }

    /**
     * 等额本息逾期还款预览
     * @param loanId
     * @param amt
     * @param date
     * @return
     * @throws BizServiceException
     */
    @Override
    public List<OverdueMonthPreviewDto> monthPreview(String loanId, BigDecimal amt, Date date) throws BizServiceException {
        List<OverdueMonthDetailDto> overdues = this.month(loanId);

        BigDecimal sum = BigDecimal.ZERO;
        for (OverdueMonthDetailDto overdue : overdues) {
            sum = sum.add(overdue.getShould());
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("overdue monthPreview OverdueMonthDetailDto,loan_id:{},{}", loanId, overdue);
            }
        }

        sum = NumberUtil.roundTwo(sum);

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("overdue monthPreview amt,loan_id:{},amt:{}", loanId, amt);
            LOGGER.debug("overdue monthPreview sum total,loan_id:{},sum:{}", loanId, sum);
        }

        if(amt == null){
            amt = sum;
        }

        if(amt.compareTo(sum) > 0){
            throw new BizServiceException(EErrorCode.LOAN_REPAY_CALC_EXCEED_MAX, String.format("module:any, %s 超过最大还款金额", loanId));
        }

        List<OverdueMonthPreviewDto> list = new ArrayList<>();

        for (OverdueMonthDetailDto overdue : overdues) {
            OverdueMonthPreviewDto preview = new OverdueMonthPreviewDto();

            BigDecimal shouldCapital = overdue.getCapital();
            BigDecimal shouldInterest = overdue.getInterest();
            BigDecimal shouldFine = overdue.getFine();

            preview.setPeriod(overdue.getPeriod());
            preview.setTotalPeriod(overdue.getTotalPeriod());

            //扣减罚息
            BigDecimal canFine = AmtUtils.min(amt, shouldFine);
            BigDecimal surplusFine = shouldFine.subtract(canFine);
            amt = AmtUtils.positive(amt.subtract(canFine));

            //扣减利息
            BigDecimal canInterest = AmtUtils.min(amt, shouldInterest);
            BigDecimal surplusInterest = shouldInterest.subtract(canInterest);
            amt = AmtUtils.positive(amt.subtract(canInterest));

            //扣减本金
            BigDecimal canCapital = AmtUtils.min(amt, shouldCapital);
            BigDecimal surplusCapital = shouldCapital.subtract(canCapital);
            amt = AmtUtils.positive(amt.subtract(canCapital));

            preview.setCapital(canCapital);
            preview.setInterest(canInterest);
            preview.setFine(canFine);
            preview.setSurplus(surplusCapital.add(surplusInterest).add(surplusFine));
            preview.setSum(canCapital.add(canInterest).add(canFine));

            list.add(preview);
        }

        return list;
    }

    /**
     * 数据校验并返回贷款和产品信息, [0]对应贷款, [1]对应产品
     * @param loanId
     * @param repayMode
     * @return
     * @throws BizServiceException
     */
    private Object[] checkAndGet(String loanId, ERepayMode repayMode) throws BizServiceException {
        LoanDtlDto loan = ConverterService.convert(loanDtlDao.findOne(loanId), LoanDtlDto.class);

        if(loan == null){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, String.format("%s 贷款不存在", loanId));
        }

        if(loan.getLoanStat() != ELoanStat.OVERDUE){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, String.format("%s 当前状态无法查看逾期概况", loanId));
        }

        if(repayMode != ERepayMode.ALL && loan.getRepayMode() != repayMode){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, String.format("%s 还款方式错误", loanId));
        }

        LoanProductDetailDto product = innerApplService.getProduct(loan.getApplId());

        return new Object[]{loan, product};
    }
}
