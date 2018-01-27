package com.xinyunlian.jinfu.loan.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.loan.enums.converter.ELoanStatEnumConverter;
import com.xinyunlian.jinfu.loan.enums.converter.ERepayModeEnumConverter;
import com.xinyunlian.jinfu.loan.enums.converter.ETermTypeEnumConverter;
import com.xinyunlian.jinfu.loan.enums.converter.ETransferStatEnumConverter;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.product.enums.converter.EIntrRateTypeEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "FP_LOAN_DTL")
//@EntityListeners(IdInjectionEntityListener.class)
public class LoanDtlPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "LOAN_ID")
    private String loanId;

    @Column(name = "APPL_ID")
    private String applId;

    @Column(name = "PRODUCT_ID")
    private String prodId;

    @Column(name = "LOAN_NAME")
    private String loanName;

    @Column(name = "ACCT_NO")
    private String acctNo;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "BANK_CARD_ID")
    private Long bankCardId;

    @Column(name = "LOAN_DT")
    private String loanDate;

    @Column(name = "LOAN_AMT")
    private BigDecimal loanAmt;

    @Column(name = "RSRV_AMT")
    private BigDecimal rsrvAmt;

    @Column(name = "TRX_MEMO")
    private String trxMemo;

    @Column(name = "REPAYED_AMT")
    private BigDecimal repayedAmt;

    @Column(name = "DUE_DT")
    private String dutDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSFER_TS")
    private Date transferDate;

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

    @Column(name = "SERVICE_FEE_RT")
    private BigDecimal serviceFeeRt;

    @Column(name = "SERVICE_FEE_MONTH_RT")
    private BigDecimal serviceFeeMonthRt;

    @Column(name = "LOAN_RT_TYPE")
    @Convert(converter = EIntrRateTypeEnumConverter.class)
    private EIntrRateType intrRateType;

    @Convert(converter = ELoanStatEnumConverter.class)
    @Column(name = "LOAN_STAT")
    private ELoanStat loanStat;

    @Column(name = "FINANCE_SOURCE_ID")
    private Integer financeSourceId;

    @Column(name = "TEST_SOURCE")
    private String testSource;

    @Column(name = "IS_DEPOSITORY")
    private Boolean depository;

    @Convert(converter = ETransferStatEnumConverter.class)
    @Column(name = "TRANSFER_STAT")
    private ETransferStat transferStat;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPL_ID", insertable = false, updatable = false)
    private LoanApplPo loanApplPo;

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

    public LoanApplPo getLoanApplPo() {
        return loanApplPo;
    }

    public void setLoanApplPo(LoanApplPo loanApplPo) {
        this.loanApplPo = loanApplPo;
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

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public String getTrxMemo() {
        return trxMemo;
    }

    public void setTrxMemo(String trxMemo) {
        this.trxMemo = trxMemo;
    }

    public BigDecimal getRepayedAmt() {
        return repayedAmt;
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

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
        //贷款编号是根据还款编号去生成的
        this.setLoanId(String.format("L%s", applId.replaceAll("[a-zA-Z]", "")));
        this.setVersionCt(0L);
    }

    public BigDecimal getRsrvAmt() {
        return rsrvAmt != null ? rsrvAmt : BigDecimal.ZERO;
    }

    public void setRsrvAmt(BigDecimal rsrvAmt) {
        this.rsrvAmt = rsrvAmt;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
