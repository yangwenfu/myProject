package com.xinyunlian.jinfu.schedule.dto.management;

import com.xinyunlian.jinfu.coupon.dto.LoanMCouponDto;
import com.xinyunlian.jinfu.loan.enums.ETransMode;
import org.apache.commons.lang.math.NumberUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 */
public class MRepayDto implements Serializable{

    private String repayId;

    private BigDecimal capital;

    private BigDecimal interest;

    private BigDecimal fine;

    private BigDecimal fee;

    private String date;

    private ETransMode transMode;

    private LoanMCouponDto coupon;

    public LoanMCouponDto getCoupon() {
        return coupon;
    }

    public void setCoupon(LoanMCouponDto coupon) {
        this.coupon = coupon;
    }

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ETransMode getTransMode() {
        return transMode;
    }

    public void setTransMode(ETransMode transMode) {
        this.transMode = transMode;
    }
}
