package com.xinyunlian.jinfu.loan.entity;

import com.xinyunlian.jinfu.audit.entity.LoanAuditPo;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.external.entity.LoanApplOutAuditPo;
import com.xinyunlian.jinfu.loan.enums.EApplChannel;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.loan.enums.converter.EApplChannelEnumConverter;
import com.xinyunlian.jinfu.loan.enums.converter.EApplStatusEnumConverter;
import com.xinyunlian.jinfu.loan.enums.converter.ERepayModeEnumConverter;
import com.xinyunlian.jinfu.loan.enums.converter.ETermTypeEnumConverter;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.product.enums.converter.EIntrRateTypeEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FP_LOAN_APPL")
@EntityListeners(IdInjectionEntityListener.class)
public class LoanApplPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "APPL_ID")
    private String applId;

    @Column(name = "PRODUCT_ID")
    private String productId;

    @Column(name = "APPL_DT")
    private String applDate;

    @Column(name = "SIGNED")
    private Boolean signed;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SIGN_TS")
    private Date signDate;

    @Convert(converter = EApplStatusEnumConverter.class)
    @Column(name = "APPL_STATUS")
    private EApplStatus applStatus;

    @Column(name = "APPL_MEMO")
    private String applMemo;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ACCT_NO")
    private String acctNo;

    @Column(name = "APPL_AMT")
    private BigDecimal applAmt;

    @Column(name = "APPR_TERM_LEN")
    private Integer apprTermLen;

    @Column(name = "APPR_AMT")
    private BigDecimal apprAmt;

    @Column(name = "TRX_MEMO")
    private String trxMemo;

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

    @Column(name = "CREDIT_LINE_RSRV_ID")
    private String creditLineRsrvId;

    @Column(name = "EXTRA_PARAMS")
    private String extraParams;

    @Column(name = "DEALER_USER_ID")
    private String dealerUserId;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "TRIAL_USER_ID")
    private String trialUserId;

    @Column(name = "REVIEW_USER_ID")
    private String reviewUserId;

    @Column(name = "HANGUP")
    private Boolean hangUp;

    @Column(name = "CHANNEL")
    @Convert(converter = EApplChannelEnumConverter.class)
    private EApplChannel channel;

    @Column(name = "LOAN_RT_TYPE")
    @Convert(converter = EIntrRateTypeEnumConverter.class)
    private EIntrRateType intrRateType;

    @Column(name = "FINANCE_SOURCE_ID")
    private Integer financeSourceId;

    @Column(name = "TRACE_ID")
    private String traceId;

    @Column(name = "TEST_SOURCE")
    private String testSource;

    @Column(name = "RISK_SCORE")
    private Double riskScore;

    @Column(name = "RISK_RESULT")
    private String riskResult;

    @OneToMany(mappedBy = "loanApplPo")
    private List<LoanAuditPo> audits;

    @OneToOne(mappedBy = "loanApplPo")
    private LoanDtlPo loanDtlPo;

    @OneToMany(mappedBy = "loanApplPo")
    private List<LoanApplOutAuditPo> loanApplOutAuditPos;

    public String getTestSource() {
        return testSource;
    }

    public void setTestSource(String testSource) {
        this.testSource = testSource;
    }

    public List<LoanApplOutAuditPo> getLoanApplOutAuditPos() {
        return loanApplOutAuditPos;
    }

    public void setLoanApplOutAuditPos(List<LoanApplOutAuditPo> loanApplOutAuditPos) {
        this.loanApplOutAuditPos = loanApplOutAuditPos;
    }

    public Integer getFinanceSourceId() {
        return financeSourceId;
    }

    public void setFinanceSourceId(Integer financeSourceId) {
        this.financeSourceId = financeSourceId;
    }

    public EApplChannel getChannel() {
        return channel;
    }

    public void setChannel(EApplChannel channel) {
        this.channel = channel;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public LoanDtlPo getLoanDtlPo() {
        return loanDtlPo;
    }

    public void setLoanDtlPo(LoanDtlPo loanDtlPo) {
        this.loanDtlPo = loanDtlPo;
    }

    public List<LoanAuditPo> getAudits() {
        return audits;
    }

    public void setAudits(List<LoanAuditPo> audits) {
        this.audits = audits;
    }

    public Boolean getHangUp() {
        return hangUp;
    }

    public void setHangUp(Boolean hangUp) {
        this.hangUp = hangUp;
    }

    public String getTrialUserId() {
        return trialUserId;
    }

    public void setTrialUserId(String trialUserId) {
        this.trialUserId = trialUserId;
    }

    public String getReviewUserId() {
        return reviewUserId;
    }

    public void setReviewUserId(String reviewUserId) {
        this.reviewUserId = reviewUserId;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getApplDate() {
        return applDate;
    }

    public void setApplDate(String applDate) {
        this.applDate = applDate;
    }

    public EApplStatus getApplStatus() {
        return applStatus;
    }

    public void setApplStatus(EApplStatus applStatus) {
        this.applStatus = applStatus;
    }

    public String getApplMemo() {
        return applMemo;
    }

    public void setApplMemo(String applMemo) {
        this.applMemo = applMemo;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public BigDecimal getApplAmt() {
        return applAmt;
    }

    public void setApplAmt(BigDecimal applAmt) {
        this.applAmt = applAmt;
    }

    public String getTrxMemo() {
        return trxMemo;
    }

    public void setTrxMemo(String trxMemo) {
        this.trxMemo = trxMemo;
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

    public String getCreditLineRsrvId() {
        return creditLineRsrvId;
    }

    public void setCreditLineRsrvId(String creditLineRsrvId) {
        this.creditLineRsrvId = creditLineRsrvId;
    }

    public String getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(String extraParams) {
        this.extraParams = extraParams;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getApprTermLen() {
        return apprTermLen;
    }

    public void setApprTermLen(Integer apprTermLen) {
        this.apprTermLen = apprTermLen;
    }

    public BigDecimal getApprAmt() {
        return apprAmt;
    }

    public void setApprAmt(BigDecimal apprAmt) {
        this.apprAmt = apprAmt;
    }

    public EIntrRateType getIntrRateType() {
        return intrRateType;
    }

    public void setIntrRateType(EIntrRateType intrRateType) {
        this.intrRateType = intrRateType;
    }

    public Integer getTermLen() {
        return termLen;
    }

    public void setTermLen(Integer termLen) {
        this.termLen = termLen;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDealerUserId() {
        return dealerUserId;
    }

    public void setDealerUserId(String dealerUserId) {
        this.dealerUserId = dealerUserId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Double riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskResult() {
        return riskResult;
    }

    public void setRiskResult(String riskResult) {
        this.riskResult = riskResult;
    }
}
