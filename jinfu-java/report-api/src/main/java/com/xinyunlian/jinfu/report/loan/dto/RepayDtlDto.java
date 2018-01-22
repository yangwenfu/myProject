package com.xinyunlian.jinfu.report.loan.dto;

import com.xinyunlian.jinfu.report.loan.enums.ETransMode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dell on 2016/11/7.
 */
public class RepayDtlDto implements Serializable {
    private String repayId;

    private String loanId;

    private String loanName;

    private String repayDate;

    private BigDecimal repayPrinAmt;

    private BigDecimal repayFee;

    private BigDecimal repayIntr;

    private BigDecimal repayFine;

    private ETransMode transMode;

    private String province;

    private String city;

    private String area;

    private String couponDesc;

    private BigDecimal couponPrice;

    private String userName;

    private String retChannelName;

    public String getRetChannelName() {
        return retChannelName;
    }

    public void setRetChannelName(String retChannelName) {
        this.retChannelName = retChannelName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public BigDecimal getRepayPrinAmt() {
        return repayPrinAmt;
    }

    public void setRepayPrinAmt(BigDecimal repayPrinAmt) {
        this.repayPrinAmt = repayPrinAmt;
    }

    public BigDecimal getRepayFee() {
        return repayFee;
    }

    public void setRepayFee(BigDecimal repayFee) {
        this.repayFee = repayFee;
    }

    public BigDecimal getRepayIntr() {
        return repayIntr;
    }

    public void setRepayIntr(BigDecimal repayIntr) {
        this.repayIntr = repayIntr;
    }

    public BigDecimal getRepayFine() {
        return repayFine;
    }

    public void setRepayFine(BigDecimal repayFine) {
        this.repayFine = repayFine;
    }

    public ETransMode getTransMode() {
        return transMode;
    }

    public void setTransMode(ETransMode transMode) {
        this.transMode = transMode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
