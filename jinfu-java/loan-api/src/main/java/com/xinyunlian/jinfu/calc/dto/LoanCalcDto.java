package com.xinyunlian.jinfu.calc.dto;

import com.xinyunlian.jinfu.coupon.dto.LoanCouponCalcDto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/1/11.
 */
public class LoanCalcDto implements Serializable{

    private BigDecimal capital;

    /**
     * 应还利息
     */
    private BigDecimal interest;

    private BigDecimal fine;

    private BigDecimal fee;

    /**
     * 剩余未还本金(随借随还)
     */
    private BigDecimal surplus;

    /**
     * 逾期天数(逾期状态下才存在)
     * @return
     */
    private Integer fineDays;

    /**
     * 叠加
     */
    private LoanCouponCalcDto coupon;

    /**
     * 原始还款总额
     * @return
     */
    private BigDecimal total;

    /**
     * 叠加活动后的
     * @return
     */
    private BigDecimal promoTotal;

    public BigDecimal getPromoTotal() {
        return promoTotal != null ? promoTotal : BigDecimal.ZERO;
    }

    public void setPromoTotal(BigDecimal promoTotal) {
        this.promoTotal = promoTotal;
    }

    public BigDecimal getTotal() {
        return total != null ? total : BigDecimal.ZERO;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LoanCouponCalcDto getCoupon() {
        return coupon;
    }

    public void setCoupon(LoanCouponCalcDto coupon) {
        this.coupon = coupon;
    }

    public Integer getFineDays() {
        return fineDays;
    }

    public void setFineDays(Integer fineDays) {
        this.fineDays = fineDays;
    }

    public BigDecimal getCapital() {
        return capital != null ? capital : BigDecimal.ZERO;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInterest() {
        return interest != null ? interest : BigDecimal.ZERO;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getFine() {
        return fine != null ? fine : BigDecimal.ZERO;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public BigDecimal getFee() {
        return fee != null ? fee : BigDecimal.ZERO;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getSurplus() {
        return surplus != null ? surplus : BigDecimal.ZERO;
    }

    public void setSurplus(BigDecimal surplus) {
        this.surplus = surplus;
    }

    @Override
    public String toString() {
        return "LoanCalcDto{" +
                "capital=" + capital +
                ", interest=" + interest +
                ", fine=" + fine +
                ", fee=" + fee +
                ", surplus=" + surplus +
                ", fineDays=" + fineDays +
                ", coupon=" + coupon +
                ", total=" + total +
                ", promoTotal=" + promoTotal +
                '}';
    }
}