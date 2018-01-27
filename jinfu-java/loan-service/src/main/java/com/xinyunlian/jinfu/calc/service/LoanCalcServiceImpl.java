package com.xinyunlian.jinfu.calc.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.LoanUtils;
import com.xinyunlian.jinfu.loan.dao.LoanDtlDao;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.service.InnerApplService;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.promo.service.InnerLoanPromoService;
import com.xinyunlian.jinfu.repay.domain.InterestPerDiem;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.enums.ELoanCustomerStatus;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import com.xinyunlian.jinfu.schedule.service.InnerScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Willwang on 2017/1/11.
 */
@Service
public class LoanCalcServiceImpl implements LoanCalcService{

    @Autowired
    private LoanDtlDao loanDtlDao;

    @Autowired
    private InnerApplService innerApplService;

    @Autowired
    private InnerScheduleService innerScheduleService;

    @Autowired
    private InnerLoanPromoService innerLoanPromoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanCalcServiceImpl.class);

    /**
     * 提前还全部计算
     * @param loanId 贷款编号
     * @return
     */
    public LoanCalcDto prepayAll(String userId, String loanId){

        LoanDtlDto loan = this.checkAndGetLoan(userId, loanId, ERepayMode.MONTH_AVE_CAP_PLUS_INTR);

        ScheduleDto scheduleCurrent;
        ScheduleDto scheduleNext;
        try {
            scheduleCurrent = innerScheduleService.getCurrentSchedule(loanId);
            scheduleNext = innerScheduleService.getNextSchedule(loanId);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID,
                String.format("module:prepayAll, %s 还款计划获取异常", loanId), e);
        }

        if(scheduleCurrent == null){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID,
                String.format("module:prepayAll, %s 当期还款计划获取异常", loanId));
        }

        BigDecimal capital = AmtUtils.positive(loan.getLoanAmt().subtract(loan.getRepayedAmt()));
        BigDecimal interest = AmtUtils.positive(scheduleCurrent.getShouldInterest().subtract(scheduleCurrent.getActualInterest()));

        LoanCalcDto loanCalcDto = new LoanCalcDto();

        loanCalcDto.setCapital(capital);
        loanCalcDto.setInterest(interest);
        loanCalcDto.setFine(BigDecimal.ZERO);
        loanCalcDto.setFee(BigDecimal.ZERO);
        loanCalcDto.setSurplus(BigDecimal.ZERO);
        if(scheduleNext != null){
            loanCalcDto.setFee(scheduleNext.getShouldInterest());
        }

        //2017年1月18日之前的订单包括1月18日不收取违约金
        try {
            if(DateHelper.before(loan.getLoanDate(), "2017-01-18")){
                loanCalcDto.setFee(BigDecimal.ZERO);
            }
        } catch (ParseException e) {
            LOGGER.error("时间转换异常", e);
        }

        return loanCalcDto;
    }

    /**
     * 提前还本期计算
     * @param userId
     * @param loanId
     * @return
     */
    @Override
    public LoanCalcDto prepayCurrent(String userId, String loanId) {
        LoanDtlDto loan = this.checkAndGetLoan(userId, loanId, ERepayMode.MONTH_AVE_CAP_PLUS_INTR);
        ScheduleDto schedule;
        try {
            schedule = innerScheduleService.getCurrentSchedule(loanId);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID,
                String.format("module:prepayCurrent, %s 还款计划获取异常", loanId), e);
        }

        if(schedule == null){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID,
                String.format("module:prepayCurrent, %s 当期还款计划获取异常", loanId));
        }

        if(schedule.getScheduleStatus() == EScheduleStatus.PAID){
            throw new BizServiceException(EErrorCode.LOAN_REPAY_CURRENT_PAID,
                    String.format("module:prepayCurrent, %s 本期已结清", loanId));
        }

        return this.calcBySchedule(loan, schedule, null);
    }

    /**
     * 等额本息按期还款
     * @param userId
     * @param loanId
     * @param period 期
     * @param date 可自定义扣款时间
     * @return
     * @throws BizServiceException
     */
    @Override
    public LoanCalcDto period(String userId, String loanId, Integer period, Date date) throws BizServiceException {
        LoanDtlDto loan = this.checkAndGetLoan(userId, loanId, ERepayMode.MONTH_AVE_CAP_PLUS_INTR);
        ScheduleDto schedule = innerScheduleService.getScheduleByPeriod(loanId, period);
        if(schedule == null){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR, "找不到有效的还款计划，无法进行计算");
        }
        return this.calcBySchedule(loan, schedule, date);
    }

    /**
     * 根据还款计划进行等额本息的还款计算
     * @param loan 贷款信息
     * @param schedule 还款计划
     * @param date 扣款日,不传则为本日
     * @return
     */
    private LoanCalcDto calcBySchedule(LoanDtlDto loan, ScheduleDto schedule, Date date){
        LoanCalcDto loanCalcDto = new LoanCalcDto();

        BigDecimal capital = AmtUtils.positive(schedule.getShouldCapital().subtract(schedule.getActualCapital()));
        BigDecimal interest = AmtUtils.positive(schedule.getShouldInterest().subtract(schedule.getActualInterest()));
        BigDecimal surplusCapital = AmtUtils.positive(loan.getLoanAmt().subtract(loan.getRepayedAmt()));

        LoanProductDetailDto product = innerApplService.getProduct(loan.getApplId());
        int fineDays = LoanUtils.getFineDays(schedule.getDueDate(), date);
        BigDecimal fine = product.getFineType().getFine(surplusCapital, fineDays, product.getFineValue());

        loanCalcDto.setCapital(capital);
        loanCalcDto.setInterest(interest);
        loanCalcDto.setFine(fine);
        loanCalcDto.setFee(BigDecimal.ZERO);
        loanCalcDto.setSurplus(BigDecimal.ZERO);
        loanCalcDto.setFineDays(fineDays);

        return loanCalcDto;
    }

    /**
     * 还任意本金的计算
     * @param userId
     * @param loanId
     * @return
     */
    @Override
    public LoanCalcDto any(String userId, String loanId, BigDecimal capital, Date date) throws BizServiceException {

        LoanDtlDto loan = this.checkAndGetLoan(userId, loanId, ERepayMode.INTR_PER_DIEM);
        //剩余可还本金
        BigDecimal surplusCapital = AmtUtils.positive(loan.getLoanAmt().subtract(loan.getRepayedAmt()));

        //如果本金没传，默认还全部剩余本金
        if(capital == null){
            capital = surplusCapital;
        }

        //还款后还剩余多少未还
        BigDecimal surplus = AmtUtils.positive(surplusCapital.subtract(capital));

        if(capital.compareTo(surplusCapital) > 0){
            throw new BizServiceException(EErrorCode.LOAN_REPAY_CALC_EXCEED_MAX,
                String.format("module:any, %s 超过最大还款金额", loanId));
        }

        loan.setLoanAmt(capital);
        //随借随还本金一直都在变，所以还款金额是根据叠加活动实时计算的
        PromoDto promoDto = innerLoanPromoService.get(loanId);
        LoanProductDetailDto product = innerApplService.getProduct(loan.getApplId());

        String forceDate = date == null ? DateHelper.getNow() : DateHelper.formatDate(date);
        InterestPerDiem repayMethod = new InterestPerDiem(loan, promoDto, forceDate, product);

        LoanCalcDto loanCalcDto = new LoanCalcDto();
        loanCalcDto.setCapital(capital);
        loanCalcDto.setInterest(repayMethod.getInterest());
        loanCalcDto.setFine(repayMethod.getFine());
        loanCalcDto.setFineDays(repayMethod.getFineInterestLength());
        loanCalcDto.setSurplus(surplus);
        loanCalcDto.setFee(BigDecimal.ZERO);
        return loanCalcDto;
    }

    @Override
    public LoanCalcDto anyForce(String userId, String loanId, BigDecimal capital, Date date){
        LoanDtlDto loan = this.checkAndGetLoan(userId, loanId, ERepayMode.INTR_PER_DIEM);

        if(capital.compareTo(BigDecimal.ZERO) <= 0){
            throw new BizServiceException(EErrorCode.LOAN_PARAM_ERROR,
                    String.format("module:any, %s 随借随还强制计算不能输入小于零的参数", loanId));
        }

        loan.setLoanAmt(capital);
        PromoDto promoDto = innerLoanPromoService.get(loanId);
        LoanProductDetailDto product = innerApplService.getProduct(loan.getApplId());

        String forceDate = date == null ? DateHelper.getNow() : DateHelper.formatDate(date);
        InterestPerDiem repayMethod = new InterestPerDiem(loan, promoDto, forceDate, product);

        LoanCalcDto loanCalcDto = new LoanCalcDto();
        loanCalcDto.setCapital(capital);
        loanCalcDto.setInterest(repayMethod.getInterest());
        loanCalcDto.setFine(repayMethod.getFine());
        loanCalcDto.setFineDays(repayMethod.getFineInterestLength());
        loanCalcDto.setFee(BigDecimal.ZERO);
        return loanCalcDto;
    }

    /**
     * 获取贷款基础信息
     * @param userId
     * @param loanId
     * @return
     */
    private LoanDtlDto checkAndGetLoan(String userId, String loanId, ERepayMode repayMode){
        LoanDtlDto loan = ConverterService.convert(loanDtlDao.findOne(loanId), LoanDtlDto.class);

        if(loan == null){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID,
                    String.format("module:checkAndGetLoan, %s 贷款数据异常", loanId));
        }

        if(StringUtils.isNotEmpty(userId) && !userId.equals(loan.getUserId())){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID,
                    String.format("module:checkAndGetLoan, %s 不能操作非本人信息", loanId));
        }

        if(loan.getRepayMode() != repayMode){
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID,
                    String.format("module:checkAndGetLoan, %s 还款类型错误", loanId));
        }

        return loan;
    }

}
