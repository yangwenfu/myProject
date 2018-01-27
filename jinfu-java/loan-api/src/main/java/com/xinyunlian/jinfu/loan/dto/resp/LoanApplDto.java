package com.xinyunlian.jinfu.loan.dto.resp;

import com.xinyunlian.jinfu.loan.enums.*;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author willwang
 */
public class LoanApplDto implements Serializable{

    private String applId;

    private EApplStatus applStatus;

    private String productId;

    private String userId;

    private String acctNo;

    private BigDecimal applAmt;

    private ETermType termType;

    private Integer termLen;

    private BigDecimal apprAmt;

    private Integer apprTermLen;

    private ERepayMode repayMode;

    private EIntrRateType intrRateType;

    private BigDecimal loanRt;

    private BigDecimal serviceFeeRt;

    private BigDecimal serviceFeeMonthRt;

    private String creditLineRsrvId;

    private String applDate;

    private Date createDate;

    private String dealerId;

    private String dealerUserId;

    private String trialUserId;

    private String reviewUserId;

    private Boolean hangUp;

    private Boolean signed;

    private EApplChannel channel;

    private String traceId;

    private Integer financeSourceId;

    private String testSource;

    private Double riskScore;

    private String riskResult;

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

    public EApplChannel getChannel() {
        return channel;
    }

    public void setChannel(EApplChannel channel) {
        this.channel = channel;
    }

    public Boolean getHangUp() {
        return hangUp;
    }

    public void setHangUp(Boolean hangUp) {
        this.hangUp = hangUp;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerUserId() {
        return dealerUserId;
    }

    public void setDealerUserId(String dealerUserId) {
        this.dealerUserId = dealerUserId;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public EApplStatus getApplStatus() {
        return applStatus;
    }

    public void setApplStatus(EApplStatus applStatus) {
        this.applStatus = applStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public ETermType getTermType() {
        return termType;
    }

    public void setTermType(ETermType termType) {
        this.termType = termType;
    }

    public Integer getTermLen() {
        return termLen;
    }

    public void setTermLen(Integer termLen) {
        this.termLen = termLen;
    }

    public BigDecimal getApprAmt() {
        return apprAmt;
    }

    public void setApprAmt(BigDecimal apprAmt) {
        this.apprAmt = apprAmt;
    }

    public Integer getApprTermLen() {
        return apprTermLen;
    }

    public void setApprTermLen(Integer apprTermLen) {
        this.apprTermLen = apprTermLen;
    }

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
    }

    public EIntrRateType getIntrRateType() {
        return intrRateType;
    }

    public void setIntrRateType(EIntrRateType intrRateType) {
        this.intrRateType = intrRateType;
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

    public String getApplDate() {
        return applDate;
    }

    public void setApplDate(String applDate) {
        this.applDate = applDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    @Override
    public String toString() {
        return "LoanApplDto{" +
                "applId='" + applId + '\'' +
                ", applStatus=" + applStatus +
                ", productId='" + productId + '\'' +
                ", userId='" + userId + '\'' +
                ", acctNo='" + acctNo + '\'' +
                ", applAmt=" + applAmt +
                ", termType=" + termType +
                ", termLen=" + termLen +
                ", apprAmt=" + apprAmt +
                ", apprTermLen=" + apprTermLen +
                ", repayMode=" + repayMode +
                ", intrRateType=" + intrRateType +
                ", loanRt=" + loanRt +
                ", creditLineRsrvId='" + creditLineRsrvId + '\'' +
                ", applDate='" + applDate + '\'' +
                ", createDate=" + createDate +
                ", dealerId='" + dealerId + '\'' +
                ", dealerUserId='" + dealerUserId + '\'' +
                ", trialUserId='" + trialUserId + '\'' +
                ", reviewUserId='" + reviewUserId + '\'' +
                ", hangUp=" + hangUp +
                ", signed=" + signed +
                ", channel=" + channel +
                ", traceId='" + traceId + '\'' +
                ", financeSourceId=" + financeSourceId +
                '}';
    }
}
