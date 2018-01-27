package com.xinyunlian.jinfu.loan.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.enums.ERepayType;
import com.xinyunlian.jinfu.loan.enums.ETransMode;
import com.xinyunlian.jinfu.loan.enums.converter.ERepayStatusEnumConverter;
import com.xinyunlian.jinfu.loan.enums.converter.ERepayTypeEnumConverter;
import com.xinyunlian.jinfu.loan.enums.converter.ETransModeEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "FP_REPAY_DTL")
@EntityListeners(IdInjectionEntityListener.class)
public class RepayDtlPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "REPAY_ID")
    private String repayId;

    @Column(name = "LOAN_ID")
    private String loanId;

    @Column(name = "ACCT_NO")
    private String acctNo;

    @Column(name = "REPAY_DT")
    private String repayDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REPAY_DATE")
    private Date repayDateTime;

    @Column(name = "REPAY_PRIN_AMT")
    private BigDecimal repayPrinAmt;

    @Column(name = "REPAY_FEE")
    private BigDecimal repayFee;

    @Column(name = "REPAY_FINE")
    private BigDecimal repayFine;

    @Column(name = "REPAY_INTR")
    private BigDecimal repayIntr;

    @Column(name = "SURPLUS_CAPTIAL")
    private BigDecimal surplusCapital;

    @Convert(converter = ETransModeEnumConverter.class)
    @Column(name = "TRANS_MODE")
    private ETransMode transMode;

    @Convert(converter = ERepayStatusEnumConverter.class)
    @Column(name = "STATUS")
    private ERepayStatus status;

    public BigDecimal getSurplusCapital() {
        return surplusCapital;
    }

    public void setSurplusCapital(BigDecimal surplusCapital) {
        this.surplusCapital = surplusCapital;
    }

    @Convert(converter = ERepayTypeEnumConverter.class)
    @Column(name = "REPAY_TYPE")
    private ERepayType repayType;

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

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
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

    public BigDecimal getRepayFine() {
        return repayFine != null ? repayFine : BigDecimal.ZERO;
    }

    public void setRepayFine(BigDecimal repayFine) {
        this.repayFine = repayFine;
    }

    public ERepayStatus getStatus() {
        return status;
    }

    public void setStatus(ERepayStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RepayDtlPo{" +
                "repayId='" + repayId + '\'' +
                ", loanId='" + loanId + '\'' +
                ", acctNo='" + acctNo + '\'' +
                ", repayDate='" + repayDate + '\'' +
                ", repayPrinAmt=" + repayPrinAmt +
                ", repayFee=" + repayFee +
                ", repayFine=" + repayFine +
                ", repayIntr=" + repayIntr +
                ", transMode=" + transMode +
                ", status=" + status +
                '}';
    }
}
