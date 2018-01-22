package com.xinyunlian.jinfu.report.allloan.entity;

import com.xinyunlian.jinfu.report.allloan.enums.ETransMode;
import com.xinyunlian.jinfu.report.allloan.enums.converter.ETransModeEnumConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dell on 2016/11/7.
 */
@Entity
@Table(name = "fp_repay_dtl_all")
public class RepayDtlAllPo implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    @Column(name = "REPAY_ID")
    private String repayId;

    @Column(name = "LOAN_ID")
    private String loanId;

    @Column(name = "LOAN_NAME")
    private String loanName;

    @Column(name = "REPAY_DT")
    private String repayDate;

    @Column(name = "REPAY_PRIN_AMT")
    private BigDecimal repayPrinAmt;

    @Column(name = "REPAY_FEE")
    private BigDecimal repayFee;

    @Column(name = "REPAY_FINE")
    private BigDecimal repayFine;

    @Column(name = "REPAY_INTR")
    private BigDecimal repayIntr;

    @Convert(converter = ETransModeEnumConverter.class)
    @Column(name = "TRANS_MODE")
    private ETransMode transMode;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "area")
    private String area;

    @Column(name = "COUPON_DESC")
    private String couponDesc;

    @Column(name = "COUPON_PRICE")
    private BigDecimal couponPrice;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "RET_CHANNEL_NAME")
    private String retChannelName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRetChannelName() {
        return retChannelName;
    }

    public void setRetChannelName(String retChannelName) {
        this.retChannelName = retChannelName;
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

    public BigDecimal getRepayFine() {
        return repayFine;
    }

    public void setRepayFine(BigDecimal repayFine) {
        this.repayFine = repayFine;
    }

    public BigDecimal getRepayIntr() {
        return repayIntr;
    }

    public void setRepayIntr(BigDecimal repayIntr) {
        this.repayIntr = repayIntr;
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
