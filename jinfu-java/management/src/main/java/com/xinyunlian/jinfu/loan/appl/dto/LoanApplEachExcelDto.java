package com.xinyunlian.jinfu.loan.appl.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 */
public class LoanApplEachExcelDto implements Serializable {


    /**
     * 申请时间
     */
    private String applDate;

    /**
     * 申请编号
     */
    private String applId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 申请人
     */
    private String userName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 申请金额
     */
    private BigDecimal applAmt;

    /**
     * 申请期限
     */
    private String applPeriod;

    /**
     * 终审额度
     * @return
     */
    private BigDecimal reviewAmt;

    /**
     * 终审期限
     */
    private String reviewPeriod;

    /**
     * 还款方式
     */
    private String repayModeString;

    /**
     * 业务来源
     */
    private String dealerTypeString;

    /**
     * 省
     */
    private String provinceAndCity;

    /**
     * 初审分配状态
     */
    private String trialClaimedTypeString;

    /**
     * 初审状态
     */
    private String trialTypeString;

    /**
     * 初审意见
     */
    private String trialStatusString;

    /**
     * 终审状态
     */
    private String reviewTypeString;

    /**
     * 终审意见
     */
    private String reviewStatusString;

    /**
     * 签约状态
     */
    private String signTypeString;

    /**
     * 签约时间
     */
    private String signDate;

    /**
     * 放款状态
     */
    private String transferStatString;

    /**
     * 放款日期
     */
    private String transferDate;

    public String getApplDate() {
        return applDate;
    }

    public void setApplDate(String applDate) {
        this.applDate = applDate;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public BigDecimal getApplAmt() {
        return applAmt;
    }

    public void setApplAmt(BigDecimal applAmt) {
        this.applAmt = applAmt;
    }

    public String getApplPeriod() {
        return applPeriod;
    }

    public void setApplPeriod(String applPeriod) {
        this.applPeriod = applPeriod;
    }

    public BigDecimal getReviewAmt() {
        return reviewAmt;
    }

    public void setReviewAmt(BigDecimal reviewAmt) {
        this.reviewAmt = reviewAmt;
    }

    public String getReviewPeriod() {
        return reviewPeriod;
    }

    public void setReviewPeriod(String reviewPeriod) {
        this.reviewPeriod = reviewPeriod;
    }

    public String getRepayModeString() {
        return repayModeString;
    }

    public void setRepayModeString(String repayModeString) {
        this.repayModeString = repayModeString;
    }

    public String getDealerTypeString() {
        return dealerTypeString;
    }

    public void setDealerTypeString(String dealerTypeString) {
        this.dealerTypeString = dealerTypeString;
    }

    public String getProvinceAndCity() {
        return provinceAndCity;
    }

    public void setProvinceAndCity(String provinceAndCity) {
        this.provinceAndCity = provinceAndCity;
    }

    public String getTrialClaimedTypeString() {
        return trialClaimedTypeString;
    }

    public void setTrialClaimedTypeString(String trialClaimedTypeString) {
        this.trialClaimedTypeString = trialClaimedTypeString;
    }

    public String getTrialTypeString() {
        return trialTypeString;
    }

    public void setTrialTypeString(String trialTypeString) {
        this.trialTypeString = trialTypeString;
    }

    public String getTrialStatusString() {
        return trialStatusString;
    }

    public void setTrialStatusString(String trialStatusString) {
        this.trialStatusString = trialStatusString;
    }

    public String getReviewTypeString() {
        return reviewTypeString;
    }

    public void setReviewTypeString(String reviewTypeString) {
        this.reviewTypeString = reviewTypeString;
    }

    public String getReviewStatusString() {
        return reviewStatusString;
    }

    public void setReviewStatusString(String reviewStatusString) {
        this.reviewStatusString = reviewStatusString;
    }

    public String getSignTypeString() {
        return signTypeString;
    }

    public void setSignTypeString(String signTypeString) {
        this.signTypeString = signTypeString;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getTransferStatString() {
        return transferStatString;
    }

    public void setTransferStatString(String transferStatString) {
        this.transferStatString = transferStatString;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }
}
