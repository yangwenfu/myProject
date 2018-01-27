package com.xinyunlian.jinfu.loan.dto.resp.apply;

import com.xinyunlian.jinfu.appl.enums.*;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.loan.enums.*;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.product.enums.ELoanProductType;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author willwang
 */
public class LoanApplyListEachDto implements Serializable {

    //申请编号
    private String applId;

    //贷款编号
    private String loanId;

    //产品编号
    private String productId;

    //产品名称
    private String productName;

    //产品类型
    private ELoanProductType productType;

    //申请人
    private String userName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 身份证号
     */
    private String idCardNo;

    //利率类型
    private EIntrRateType rateType;

    //利率
    private BigDecimal rate;

    //还款方式
    private ERepayMode repayMode;

    //申请类型
    private EApplStatus status;

    //申请时间
    private String applDate;

    //申请时间
    private Date createDate;

    /**
     * 期数
     */
    private Integer period;

    /**
     * 单位
     */
    private String unit;

    private BigDecimal applAmt;

    private String userId;

    /**
     * 初审分配状态
     */
    private ELoanApplClaimedType trialClaimedType;

    /**
     * 初审状态
     */
    private ELoanApplTrialType trialType;

    /**
     * 终审状态
     */
    private ELoanApplReviewType reviewType;

    /**
     * 业务来源
     */
    private ELoanApplDealerType dealerType;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 初审人编号
     */
    private String trialUserId;

    /**
     * 初审人名字
     */
    private String trialName;

    /**
     * 终审人编号
     */
    private String reviewUserId;

    /**
     * 终审人名字
     */
    private String reviewName;

    /**
     * 分销员编号
     */
    private String dealerUserId;

    /**
     * 分销员名字
     */
    private String dealerUserName;

    /**
     * 分销商编号
     */
    private String dealerId;

    /**
     * 分销商名字
     */
    private String dealerName;

    /**
     * 初审额度
     * @return
     */
    private BigDecimal trialAmt;

    /**
     * 放款金额
     */
    private BigDecimal loanAmt;

    /**
     * 还款期限
     */
    private Integer loanPeriod;

    /**
     * 终审额度
     * @return
     */
    private BigDecimal reviewAmt;

    /**
     * 是否挂起
     */
    private Boolean hangup;

    /**
     * 状态状态
     */
    private ETransferStat transferStat;

    /**
     * 最近一条收付指令
     */
    private PayRecvOrdDto payRecvOrd;

    /**
     * 放款时间
     * @return
     */
    private String transferDate;

    /**
     * 签约状态
     */
    private ELoanApplSignType signType;

    /**
     * 签约时间
     */
    private String signDate;

    /**
     * 初审意见
     */
    private EAuditStatus trialStatus;

    /**
     * 终审意见
     * @return
     */
    private EAuditStatus reviewStatus;

    /**
     * 资金路由编号
     */
    private Integer financeSourceId;

    /**
     * 评分
     */
    private Double riskScore;

    /**
     * 风控决策
     */
    private String riskResult;

    /**
     * 资金路由
     * @return
     */
    private EFinanceSourceType financeSourceType;

    /**
     * 外部审核金额
     */
    private BigDecimal outAuditLoanAmt;

    /**
     * 外部审核状态
     */
    private EApplOutAuditType outAuditResult;

    /**
     * 外部审核描述
     */
    private String outAuditReason;

    public BigDecimal getOutAuditLoanAmt() {
        return outAuditLoanAmt;
    }

    public void setOutAuditLoanAmt(BigDecimal outAuditLoanAmt) {
        this.outAuditLoanAmt = outAuditLoanAmt;
    }

    public EApplOutAuditType getOutAuditResult() {
        return outAuditResult;
    }

    public void setOutAuditResult(EApplOutAuditType outAuditResult) {
        this.outAuditResult = outAuditResult;
    }

    public String getOutAuditReason() {
        return outAuditReason;
    }

    public void setOutAuditReason(String outAuditReason) {
        this.outAuditReason = outAuditReason;
    }

    public Integer getFinanceSourceId() {
        return financeSourceId;
    }

    public void setFinanceSourceId(Integer financeSourceId) {
        this.financeSourceId = financeSourceId;
    }

    public EFinanceSourceType getFinanceSourceType() {
        return financeSourceType;
    }

    public void setFinanceSourceType(EFinanceSourceType financeSourceType) {
        this.financeSourceType = financeSourceType;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public ELoanApplReviewType getReviewType() {
        return reviewType;
    }

    public void setReviewType(ELoanApplReviewType reviewType) {
        this.reviewType = reviewType;
    }

    public ELoanApplTrialType getTrialType() {
        return trialType;
    }

    public void setTrialType(ELoanApplTrialType trialType) {
        this.trialType = trialType;
    }

    public EAuditStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(EAuditStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public EAuditStatus getTrialStatus() {
        return trialStatus;
    }

    public void setTrialStatus(EAuditStatus trialStatus) {
        this.trialStatus = trialStatus;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public ELoanApplSignType getSignType() {
        return signType;
    }

    public void setSignType(ELoanApplSignType signType) {
        this.signType = signType;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public ETransferStat getTransferStat() {
        return transferStat;
    }

    public void setTransferStat(ETransferStat transferStat) {
        this.transferStat = transferStat;
    }

    public PayRecvOrdDto getPayRecvOrd() {
        return payRecvOrd;
    }

    public void setPayRecvOrd(PayRecvOrdDto payRecvOrd) {
        this.payRecvOrd = payRecvOrd;
    }

    public String getReviewUserId() {
        return reviewUserId;
    }

    public void setReviewUserId(String reviewUserId) {
        this.reviewUserId = reviewUserId;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public Boolean getHangup() {
        return hangup;
    }

    public void setHangup(Boolean hangup) {
        this.hangup = hangup;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getTrialAmt() {
        return trialAmt;
    }

    public void setTrialAmt(BigDecimal trialAmt) {
        this.trialAmt = trialAmt;
    }

    public BigDecimal getReviewAmt() {
        return reviewAmt;
    }

    public void setReviewAmt(BigDecimal reviewAmt) {
        this.reviewAmt = reviewAmt;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ELoanProductType getProductType() {
        return productType;
    }

    public void setProductType(ELoanProductType productType) {
        this.productType = productType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public EIntrRateType getRateType() {
        return rateType;
    }

    public void setRateType(EIntrRateType rateType) {
        this.rateType = rateType;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
    }

    public EApplStatus getStatus() {
        return status;
    }

    public void setStatus(EApplStatus status) {
        this.status = status;
    }

    public String getApplDate() {
        return applDate;
    }

    public void setApplDate(String applDate) {
        this.applDate = applDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getApplAmt() {
        return applAmt;
    }

    public void setApplAmt(BigDecimal applAmt) {
        this.applAmt = applAmt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ELoanApplClaimedType getTrialClaimedType() {
        return trialClaimedType;
    }

    public void setTrialClaimedType(ELoanApplClaimedType trialClaimedType) {
        this.trialClaimedType = trialClaimedType;
    }

    public ELoanApplDealerType getDealerType() {
        return dealerType;
    }

    public void setDealerType(ELoanApplDealerType dealerType) {
        this.dealerType = dealerType;
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

    public String getTrialUserId() {
        return trialUserId;
    }

    public void setTrialUserId(String trialUserId) {
        this.trialUserId = trialUserId;
    }

    public String getTrialName() {
        return trialName;
    }

    public void setTrialName(String trialName) {
        this.trialName = trialName;
    }

    public String getDealerUserId() {
        return dealerUserId;
    }

    public void setDealerUserId(String dealerUserId) {
        this.dealerUserId = dealerUserId;
    }

    public String getDealerUserName() {
        return dealerUserName;
    }

    public void setDealerUserName(String dealerUserName) {
        this.dealerUserName = dealerUserName;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
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
