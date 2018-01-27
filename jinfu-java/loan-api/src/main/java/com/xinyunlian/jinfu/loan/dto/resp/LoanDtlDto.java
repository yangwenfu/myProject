package com.xinyunlian.jinfu.loan.dto.resp;

import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author willwang
 */
public class LoanDtlDto implements Serializable {

    private String loanId;

    private String applId;

    private String prodId;

    private String loanName;

    private String acctNo;

    private String userId;

    private String loanDate;

    private Long bankCardId;

    private BigDecimal loanAmt;

    private BigDecimal rsrvAmt;

    private String trxMemo;

    private BigDecimal repayedAmt;

    private String dutDate;

    private Date transferDate;

    private ETransferStat transferStat;

    private Integer termLen;

    private ETermType termType;

    private ERepayMode repayMode;

    private BigDecimal loanRt;

    /**
     * 咨询服务费率
     */
    private BigDecimal serviceFeeRt;

    /**
     * 月服务费率
     */
    private BigDecimal serviceFeeMonthRt;

    private EIntrRateType intrRateType;

    private ELoanStat loanStat;

    private Integer financeSourceId;

    private String testSource;

    private Boolean depository;

    public Boolean getDepository() {
        return depository;
    }

    public void setDepository(Boolean depository) {
        this.depository = depository;
    }

    public String getTestSource() {
        return testSource;
    }

    public void setTestSource(String testSource) {
        this.testSource = testSource;
    }

    public Integer getFinanceSourceId() {
        return financeSourceId;
    }

    public void setFinanceSourceId(Integer financeSourceId) {
        this.financeSourceId = financeSourceId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
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

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt != null ? loanAmt : BigDecimal.ZERO;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public BigDecimal getRsrvAmt() {
        return rsrvAmt != null ? rsrvAmt : BigDecimal.ZERO;
    }

    public void setRsrvAmt(BigDecimal rsrvAmt) {
        this.rsrvAmt = rsrvAmt;
    }

    public String getTrxMemo() {
        return trxMemo;
    }

    public void setTrxMemo(String trxMemo) {
        this.trxMemo = trxMemo;
    }

    public BigDecimal getRepayedAmt() {
        return repayedAmt != null ? repayedAmt : BigDecimal.ZERO;
    }

    public void setRepayedAmt(BigDecimal repayedAmt) {
        this.repayedAmt = repayedAmt;
    }

    public String getDutDate() {
        return dutDate;
    }

    public void setDutDate(String dutDate) {
        this.dutDate = dutDate;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
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

    public ELoanStat getLoanStat() {
        return loanStat;
    }

    public void setLoanStat(ELoanStat loanStat) {
        this.loanStat = loanStat;
    }

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public ETransferStat getTransferStat() {
        return transferStat;
    }

    public void setTransferStat(ETransferStat transferStat) {
        this.transferStat = transferStat;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public EIntrRateType getIntrRateType() {
        return intrRateType;
    }

    public void setIntrRateType(EIntrRateType intrRateType) {
        this.intrRateType = intrRateType;
    }

    public BigDecimal getServiceFeeRt() {
        return serviceFeeRt;
    }

    public void setServiceFeeRt(BigDecimal serviceFeeRt) {
        this.serviceFeeRt = serviceFeeRt;
    }

    public BigDecimal getServiceFeeMonthRt() {
        return serviceFeeMonthRt;
    }

    public void setServiceFeeMonthRt(BigDecimal serviceFeeMonthRt) {
        this.serviceFeeMonthRt = serviceFeeMonthRt;
    }

    @Override
    public String toString() {
        return "LoanDtlDto{" +
                "loanId='" + loanId + '\'' +
                ", applId='" + applId + '\'' +
                ", prodId='" + prodId + '\'' +
                ", loanName='" + loanName + '\'' +
                ", acctNo='" + acctNo + '\'' +
                ", userId='" + userId + '\'' +
                ", loanDate='" + loanDate + '\'' +
                ", bankCardId=" + bankCardId +
                ", loanAmt=" + loanAmt +
                ", rsrvAmt=" + rsrvAmt +
                ", trxMemo='" + trxMemo + '\'' +
                ", repayedAmt=" + repayedAmt +
                ", dutDate='" + dutDate + '\'' +
                ", transferDate=" + transferDate +
                ", transferStat=" + transferStat +
                ", termLen=" + termLen +
                ", termType=" + termType +
                ", repayMode=" + repayMode +
                ", loanRt=" + loanRt +
                ", intrRateType=" + intrRateType +
                ", loanStat=" + loanStat +
                ", financeSourceId=" + financeSourceId +
                '}';
    }
}
