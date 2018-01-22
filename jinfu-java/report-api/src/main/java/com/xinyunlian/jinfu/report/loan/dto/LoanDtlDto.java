package com.xinyunlian.jinfu.report.loan.dto;

import com.xinyunlian.jinfu.report.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.report.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.report.loan.enums.ETermType;
import com.xinyunlian.jinfu.report.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.report.loan.enums.EIntrRateType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dell on 2016/11/7.
 */
public class LoanDtlDto implements Serializable {

    private String loanId;

    private String userId;

    private String applId;

    private String loanName;

    private String userName;

    private Date transferDate;

    private BigDecimal loanAmt;

    private BigDecimal repayedAmt;

    private String dueDate;

    private ETransferStat transferStat;

    private Integer termLen;

    private ETermType termType;

    private ERepayMode repayMode;

    private BigDecimal loanRt;

    private EIntrRateType intrRateType;

    private ELoanStat loanStat;

    private String province;

    private String city;

    private String area;

    private String promoId;

    private String promoName;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public BigDecimal getRepayedAmt() {
        return repayedAmt;
    }

    public void setRepayedAmt(BigDecimal repayedAmt) {
        this.repayedAmt = repayedAmt;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public ETransferStat getTransferStat() {
        return transferStat;
    }

    public void setTransferStat(ETransferStat transferStat) {
        this.transferStat = transferStat;
    }

    public Integer getTermLen() {
        return termLen;
    }

    public void setTermLen(Integer termLen) {
        this.termLen = termLen;
    }

    public ETermType getTermType() {
        return termType;
    }

    public void setTermType(ETermType termType) {
        this.termType = termType;
    }

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
    }

    public BigDecimal getLoanRt() {
        return loanRt;
    }

    public void setLoanRt(BigDecimal loanRt) {
        this.loanRt = loanRt;
    }

    public EIntrRateType getIntrRateType() {
        return intrRateType;
    }

    public void setIntrRateType(EIntrRateType intrRateType) {
        this.intrRateType = intrRateType;
    }

    public ELoanStat getLoanStat() {
        return loanStat;
    }

    public void setLoanStat(ELoanStat loanStat) {
        this.loanStat = loanStat;
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

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }
}
