package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QunarApplyResultResponse implements Serializable{
    private static final long serialVersionUID = 4392479300990113742L;

    @JsonProperty("insurantName")
    private String insurantName;
    @JsonProperty("certificateNo")
    private String certificateNo;
    @JsonProperty("applicantName")
    private String applicantName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("mobileTelephone")
    private String mobileTelphone;
    @JsonProperty("applyPolicyNo")
    private String applyPolicyNo;
    @JsonProperty("policyNo")
    private String policyNo;
    @JsonProperty("noticeNo")
    private String noticeNo;
    @JsonProperty("totalInsuredAmount")
    private BigDecimal totalInsuredAmount;
    @JsonProperty("amountCurrencyCode")
    private String amountCurrencyCode;
    @JsonProperty("totalActualPremium")
    private BigDecimal totalActualPremium;
    @JsonProperty("premiumCurrencyCode")
    private String premiumCurrencyCode;
    @JsonProperty("insuranceBeginDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date insuranceBeginDate;
    @JsonProperty("insuranceEndDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
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
}
