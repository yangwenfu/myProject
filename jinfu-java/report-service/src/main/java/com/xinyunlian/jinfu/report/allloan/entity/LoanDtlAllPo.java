package com.xinyunlian.jinfu.report.allloan.entity;

import com.xinyunlian.jinfu.report.allloan.enums.*;
import com.xinyunlian.jinfu.report.allloan.enums.converter.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dell on 2016/11/7.
 */
@Entity
@Table(name = "fp_loan_dtl_all")
public class LoanDtlAllPo implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    @Column(name = "LOAN_ID")
    private String loanId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "APPL_ID")
    private String applId;

    @Column(name = "LOAN_NAME")
    private String loanName;

    @Column(name = "USER_NAME")
    private String userName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSFER_TS")
    private Date transferDate;

    @Column(name = "LOAN_AMT")
    private BigDecimal loanAmt;

    @Column(name = "REPAYED_AMT")
    private BigDecimal repayedAmt;

    @Column(name = "DUE_DT")
    private String dueDate;

    @Convert(converter = ETransferStatEnumConverter.class)
    @Column(name = "TRANSFER_STAT")
    private ETransferStat transferStat;

    @Column(name = "TERM_LEN")
    private Integer termLen;

    @Convert(converter = ETermTypeEnumConverter.class)
    @Column(name = "TERM_TYPE")
    private ETermType termType;

    @Convert(converter = ERepayModeEnumConverter.class)
    @Column(name = "REPAY_MODE")
    private ERepayMode repayMode;

    @Column(name = "LOAN_RT")
    private BigDecimal loanRt;

    @Convert(converter = EIntrRateTypeEnumConverter.class)
    @Column(name = "LOAN_RT_TYPE")
    private EIntrRateType intrRateType;

    @Convert(converter = ELoanStatEnumConverter.class)
    @Column(name = "LOAN_STAT")
    private ELoanStat loanStat;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "area")
    private String area;

    @Column(name = "PROMO_ID")
    private String promoId;

    @Column(name = "PROMO_NAME")
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
