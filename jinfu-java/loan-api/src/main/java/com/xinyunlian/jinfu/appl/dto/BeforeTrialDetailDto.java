package com.xinyunlian.jinfu.appl.dto;

import com.xinyunlian.jinfu.appl.enums.ELoanApplDealerType;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/2/13.
 * 初审进件详情
 */
public class BeforeTrialDetailDto implements Serializable{

    /**
     * 贷款编号(系统内为申请编号)
     */
    private String applId;

    /**
     * 贷款编号
     */
    private String loanId;

    /**
     * 贷款申请状态
     */
    private EApplStatus status;

    /**
     * 申请用户ID
     */
    private String userId;

    /**
     * 申请日期
     */
    private String applDate;

    /**
     * 业务来源
     */
    private ELoanApplDealerType dealerType;

    /**
     * 分销员
     */
    private String dealerUserName;

    /**
     * 分销商
     */
    private String dealerName;

    /**
     * 分销编号
     */
    private String dealerId;

    /**
     * 分销员编号
     */
    private String dealerUserId;

    /**
     *  借款人姓名
     */
    private String userName;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 申请金额
     */
    private BigDecimal applAmt;

    /**
     * 申请期限
     */
    private Integer period;

    /**
     * 期限类型
     */
    private String unit;

    private Long promoId;

    private String promoName;

    private String promoDesc;

    /**
     * 挂起状态
     */
    private Boolean hangUp;

    /**
     * 签约状态
     */
    private Boolean signed;

    /**
     * 产品信息
     */
    private LoanProductDetailDto product;

    /**
     * 是否可以初审撤销
     */
    private Boolean canTrialRevoke;

    /**
     * 是否可以初审重新分配
     */
    private Boolean canTrialAssign;

    /**
     * 是否可以终审重新分配
     */
    private Boolean canReviewAssign;

    /**
     * 是否可以终审撤销
     */
    private Boolean canReviewRevoke;

    /**
     * 资金路由配置
     */
    private EFinanceSourceType financeSourceType;

    /**
     * 风控评分
     */
    private Double riskScore;

    /**
     * 风控决策建议
     */
    private String riskResult;

    public EFinanceSourceType getFinanceSourceType() {
        return financeSourceType;
    }

    public void setFinanceSourceType(EFinanceSourceType financeSourceType) {
        this.financeSourceType = financeSourceType;
    }

    public Boolean getCanReviewRevoke() {
        return canReviewRevoke;
    }

    public void setCanReviewRevoke(Boolean canReviewRevoke) {
        this.canReviewRevoke = canReviewRevoke;
    }

    public Boolean getCanTrialRevoke() {
        return canTrialRevoke;
    }

    public void setCanTrialRevoke(Boolean canTrialRevoke) {
        this.canTrialRevoke = canTrialRevoke;
    }

    public Boolean getCanTrialAssign() {
        return canTrialAssign;
    }

    public void setCanTrialAssign(Boolean canTrialAssign) {
        this.canTrialAssign = canTrialAssign;
    }

    public Boolean getCanReviewAssign() {
        return canReviewAssign;
    }

    public void setCanReviewAssign(Boolean canReviewAssign) {
        this.canReviewAssign = canReviewAssign;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
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

    public String getApplDate() {
        return applDate;
    }

    public void setApplDate(String applDate) {
        this.applDate = applDate;
    }

    public ELoanApplDealerType getDealerType() {
        return dealerType;
    }

    public void setDealerType(ELoanApplDealerType dealerType) {
        this.dealerType = dealerType;
    }

    public String getDealerUserName() {
        return dealerUserName;
    }

    public void setDealerUserName(String dealerUserName) {
        this.dealerUserName = dealerUserName;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getApplAmt() {
        return applAmt;
    }

    public void setApplAmt(BigDecimal applAmt) {
        this.applAmt = applAmt;
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

    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(Long promoId) {
        this.promoId = promoId;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public String getPromoDesc() {
        return promoDesc;
    }

    public void setPromoDesc(String promoDesc) {
        this.promoDesc = promoDesc;
    }

    public LoanProductDetailDto getProduct() {
        return product;
    }

    public void setProduct(LoanProductDetailDto product) {
        this.product = product;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public EApplStatus getStatus() {
        return status;
    }

    public void setStatus(EApplStatus status) {
        this.status = status;
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

