package com.xinyunlian.jinfu.zhongan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/3/14/0014.
 */
public class VehicleInsNotifyStringDto implements Serializable {
    private static final long serialVersionUID = -5103540757814416678L;

    @JsonProperty("notifyType")
    private String notifyType;

    @JsonProperty("policyNo")
    private String policyNo;

    @JsonProperty("sumInsured")
    private Double sumInsured;

    @JsonProperty("premium")
    private Double premium;

    @JsonProperty("effectiveDate")
    private String effectiveDate;

    @JsonProperty("expiryDate")
    private String expiryDate;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("promoteCode")
    private String promoteCode;

    @JsonProperty("promoteName")
    private String promoteName;

    @JsonProperty("bizContent")
    private String bizContent;

    @JsonProperty("promoteFee")
    private Double promoteFee;

    @JsonProperty("vehicleType")
    private String vehicleType;

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public Double getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPromoteCode() {
        return promoteCode;
    }

    public void setPromoteCode(String promoteCode) {
        this.promoteCode = promoteCode;
    }

    public String getPromoteName() {
        return promoteName;
    }

    public void setPromoteName(String promoteName) {
        this.promoteName = promoteName;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public Double getPromoteFee() {
        return promoteFee;
    }

    public void setPromoteFee(Double promoteFee) {
        this.promoteFee = promoteFee;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
