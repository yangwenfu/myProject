package com.xinyunlian.jinfu.repay.domain;

import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 随借随还，按日计息
 * Created by JL on 2016/11/22.
 */
public class InterestPerDiem extends RepayMethod {

    private String loanDt;

    private String targetDt;

    /**
     * 产品信息
     */
    private LoanProductDetailDto product;

    /**
     * 用于缓存原始计息长度，活动计算后恢复
     */
    private int originInterestLength = 0;

    private int interestLength = 0;

    private int fineInterestLength = 0;

    private int totalInterestLength = 0;

    /**
     * @param loanDtlDto  贷款信息
     * @param promoDto    优惠活动
     * @param targetDt    还款时间
     */
    public InterestPerDiem(LoanDtlDto loanDtlDto, PromoDto promoDto, String targetDt, LoanProductDetailDto product) {
        super(loanDtlDto, promoDto, new Date());
        this.product = product;
        this.setRt(this.getIntrRateType().getDay(this.getRt()));
        this.setIntrRateType(EIntrRateType.DAY);
        this.setTermLen(this.getTermType().getDay(this.getTermLen()));
        this.setTermType(ETermType.DAY);
        this.loanDt = DateHelper.formatDate(loanDtlDto.getTransferDate());
        this.targetDt = targetDt;
        this.calInterestLength();
        this.calFineInterestLength();
    }


    /**
     * 获得本金
     *
     * @return
     */
    @Override
    public BigDecimal getCapital() {
        return this.getLoanAmt();
    }

    /**
     * 获得利息
     *
     * @return
     */
    @Override
    public BigDecimal getInterest() {
        BigDecimal promoInterest = calPromo();
        BigDecimal interest = calIntr(interestLength, this.getRt(), this.getLoanAmt()).add(promoInterest).setScale(2, BigDecimal.ROUND_HALF_UP);
        //计息长度恢复至活动计算前
        this.interestLength = this.originInterestLength;
        return interest;
    }

    /**
     * 获得还款计划
     *
     * @return
     */
    @Override
    public List<ScheduleDto> getRepaySchedule() {
        List<ScheduleDto> list = new ArrayList<>();
        ScheduleDto schedule = new ScheduleDto();
        schedule.setLoanId(this.getLoanDtlDto().getLoanId());
        schedule.setAcctNo(this.getLoanDtlDto().getAcctNo());
        schedule.setDueDate(getLoanDtlDto().getDutDate());
        schedule.setSeqNo(1);
        schedule.setShouldCapital(this.getCapital());
        schedule.setShouldInterest(this.getInterest());
        schedule.setScheduleStatus(EScheduleStatus.NOTYET);
        list.add(schedule);
        return list;
    }

    /**
     * 计算计息长度
     */
    private void calInterestLength() {
        Date loanDate = DateHelper.getDate(loanDt);
        Date dueDate = DateHelper.getDate(targetDt);

        //最小还款天数
        int minIntrDays = product.getMinIntrDays() != null ? product.getMinIntrDays() : 0;

        int diff = DateHelper.betweenDaysNew(loanDate, dueDate);
        this.totalInterestLength = diff < minIntrDays ? minIntrDays : diff;
        this.interestLength = this.totalInterestLength < this.getTermLen() ? this.totalInterestLength : this.getTermLen();
    }

    /**
     * 计算罚息长度
     */
    private void calFineInterestLength() {
        this.fineInterestLength = this.totalInterestLength > this.getTermLen() ? this.totalInterestLength - this.getTermLen() : 0;
    }

    /**
     * 计算促销
     */
    private BigDecimal calPromo() {
        PromoDto promoDto = this.getPromoDto();
        BigDecimal intr = BigDecimal.ZERO;
        if (promoDto != null) {
            switch (promoDto.getPromoType()) {
                case RATE:
                    if (this.interestLength < promoDto.getPromoLen()) {
                        intr = calIntr(interestLength, this.getRt().multiply(promoDto.getPromoValue()), this.getLoanAmt());
                        this.originInterestLength = this.interestLength;
                        this.interestLength = 0;
                    } else {
                        intr = calIntr(promoDto.getPromoLen(), this.getRt().multiply(promoDto.getPromoValue()), this.getLoanAmt());
                        this.originInterestLength = this.interestLength;
                        this.interestLength = this.interestLength - promoDto.getPromoLen();
                    }
                    break;
                case MONEY:
                    break;
                case OFFLINE:
                    //线下优惠，不做任何操作
                    break;
            }
        }
        return intr;
    }

    /**
     * 计算利息公式
     *
     * @param length
     * @param rt
     * @param amt
     * @return
     */
    private BigDecimal calIntr(int length, BigDecimal rt, BigDecimal amt) {
        return BigDecimal.valueOf(length).multiply(rt).multiply(amt);
    }


    @Override
    public BigDecimal getFine() {
        BigDecimal fine = this.product.getFineType().getFine(
            this.getLoanAmt(), fineInterestLength, this.product.getFineValue()
        );
        return NumberUtil.roundTwo(fine);
    }

    /**
     * 获取罚息计息天数
     * @return
     */
    public int getFineInterestLength() {
        return fineInterestLength;
    }

}
