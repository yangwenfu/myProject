package com.xinyunlian.jinfu.balance.dto;

import com.xinyunlian.jinfu.coupon.dto.LoanCouponRepayDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Willwang on 2017/5/23.
 */
public class BalanceDetailInteriorDto implements Serializable{

    private Long detailId;

    private Date repayDate;

    private String repayDateStr;

    private String repayId;

    private BigDecimal repayAmt;

    private String channelCode;

    private String channelName;

    private String balanceDate;

    private String remark;

    private BigDecimal capital;

    private BigDecimal interest;

    private BigDecimal fine;

    private BigDecimal fee;

    private LoanCouponRepayDto coupon;

    public LoanCouponRepayDto getCoupon() {
        return coupon;
    }

    public void setCoupon(LoanCouponRepayDto coupon) {
        this.coupon = coupon;
    }

    public String getRepayDateStr() {
        return repayDateStr;
    }

    public void setRepayDateStr(String repayDateStr) {
        this.repayDateStr = repayDateStr;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

    public BigDecimal getRepayAmt() {
        return repayAmt;
    }

    public void setRepayAmt(BigDecimal repayAmt) {
        this.repayAmt = repayAmt;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(String balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
