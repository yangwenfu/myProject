package com.xinyunlian.jinfu.repay.dto;

import com.xinyunlian.jinfu.coupon.dto.LoanCouponRepayDto;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.enums.ETransMode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 */
public class LoanRepayRespDto implements Serializable{

    private String repayId;

    private BigDecimal amt;

    private BigDecimal capital;

    private BigDecimal interest;

    private BigDecimal fine;

    private BigDecimal fee;

    private String repayDate;

    private ETransMode transMode;

    private ERepayStatus repayStatus;

    private LoanCouponRepayDto coupon;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoanCouponRepayDto getCoupon() {
        return coupon;
    }

    public void setCoupon(LoanCouponRepayDto coupon) {
        this.coupon = coupon;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public ETransMode getTransMode() {
        return transMode;
    }

    public void setTransMode(ETransMode transMode) {
        this.transMode = transMode;
    }

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

    public ERepayStatus getRepayStatus() {

        //数据库中部分脏数据存在状态为null的情况，再历史数据中,null即代表成功
        if(this.repayStatus == null){
            return ERepayStatus.SUCCESS;
        }

        return repayStatus;
    }

    public void setRepayStatus(ERepayStatus repayStatus) {
        this.repayStatus = repayStatus;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }
}
