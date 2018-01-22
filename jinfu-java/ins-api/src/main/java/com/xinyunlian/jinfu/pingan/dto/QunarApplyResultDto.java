package com.xinyunlian.jinfu.pingan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
public class QunarApplyResultDto implements Serializable{

    private static final long serialVersionUID = 8364575933354507705L;

    private String responseCode;

    private String responseMsg;

    private String insurantName;

    private String certificateNo;

    private String applicantName;

    private String address;

    private String mobileTelphone;

    private String applyPolicyNo;

    private String policyNo;

    private String noticeNo;

    private BigDecimal totalInsuredAmount;

    private String amountCurrencyCode;

    private BigDecimal totalActualPremium;

    private String premiumCurrencyCode;

    private Date insuranceBeginDate;

    private Date insuranceEndDate;

    public String getInsurantName() {
        return insurantName;
    }

    public void setInsurantName(String insurantName) {
        this.insurantName = insurantName;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileTelphone() {
        return mobileTelphone;
    }

    public void setMobileTelphone(String mobileTelphone) {
        this.mobileTelphone = mobileTelphone;
    }

    public String getApplyPolicyNo() {
        return applyPolicyNo;
    }

    public void setApplyPolicyNo(String applyPolicyNo) {
        this.applyPolicyNo = applyPolicyNo;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getAmountCurrencyCode() {
        return amountCurrencyCode;
    }

    public void setAmountCurrencyCode(String amountCurrencyCode) {
        this.amountCurrencyCode = amountCurrencyCode;
    }

    public BigDecimal getTotalInsuredAmount() {
        return totalInsuredAmount;
    }

    public void setTotalInsuredAmount(BigDecimal totalInsuredAmount) {
        this.totalInsuredAmount = totalInsuredAmount;
    }

    public BigDecimal getTotalActualPremium() {
        return totalActualPremium;
    }

    public void setTotalActualPremium(BigDecimal totalActualPremium) {
        this.totalActualPremium = totalActualPremium;
    }

    public String getPremiumCurrencyCode() {
        return premiumCurrencyCode;
    }

    public void setPremiumCurrencyCode(String premiumCurrencyCode) {
        this.premiumCurrencyCode = premiumCurrencyCode;
    }

    public Date getInsuranceBeginDate() {
        return insuranceBeginDate;
    }

    public void setInsuranceBeginDate(Date insuranceBeginDate) {
        this.insuranceBeginDate = insuranceBeginDate;
    }

    public Date getInsuranceEndDate() {
        return insuranceEndDate;
    }

    public void setInsuranceEndDate(Date insuranceEndDate) {
        this.insuranceEndDate = insuranceEndDate;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}
