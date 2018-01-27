package com.xinyunlian.jinfu.loan.dto;

import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.enums.ERepayType;
import com.xinyunlian.jinfu.loan.enums.ETransMode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Willwang
 */
public class RepayDtlDto implements Serializable {
    private String repayId;

    private String loanId;

    private String loanName;

    private String repayDate;

    private Date repayDateTime;

    private BigDecimal repayPrinAmt;

    private BigDecimal repayFee;

    private BigDecimal repayFine;

    private BigDecimal repayIntr;

    private ETransMode transMode;

    private ERepayStatus status;

    private ERepayType repayType;

    private String acctNo;

    public Date getRepayDateTime() {
        return repayDateTime;
    }

    public void setRepayDateTime(Date repayDateTime) {
        this.repayDateTime = repayDateTime;
    }

    public ERepayType getRepayType() {
        return repayType;
    }

    public void setRepayType(ERepayType repayType) {
        this.repayType = repayType;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
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
        return repayPrinAmt != null ? repayPrinAmt : BigDecimal.ZERO;
    }

    public void setRepayPrinAmt(BigDecimal repayPrinAmt) {
        this.repayPrinAmt = repayPrinAmt;
    }

    public BigDecimal getRepayFee() {
        return repayFee != null ? repayFee : BigDecimal.ZERO;
    }

    public void setRepayFee(BigDecimal repayFee) {
        this.repayFee = repayFee;
    }

    public BigDecimal getRepayFine() {
        return repayFine != null ? repayFine : BigDecimal.ZERO;
    }

    public void setRepayFine(BigDecimal repayFine) {
        this.repayFine = repayFine;
    }

    public BigDecimal getRepayIntr() {
        return repayIntr != null ? repayIntr : BigDecimal.ZERO;
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

    public ERepayStatus getStatus() {
        return status;
    }

    public void setStatus(ERepayStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal(){
        return this.getRepayPrinAmt()
                .add(this.getRepayIntr())
                .add(this.getRepayFine())
                .add(this.getRepayFee());
    }

    @Override
    public String toString() {
        return "RepayDtlDto{" +
                "repayId='" + repayId + '\'' +
                ", loanId='" + loanId + '\'' +
                ", loanName='" + loanName + '\'' +
                ", repayDate='" + repayDate + '\'' +
                ", repayPrinAmt=" + repayPrinAmt +
                ", repayFee=" + repayFee +
                ", repayFine=" + repayFine +
                ", repayIntr=" + repayIntr +
                ", acctNo='" + acctNo + '\'' +
                '}';
    }
}
