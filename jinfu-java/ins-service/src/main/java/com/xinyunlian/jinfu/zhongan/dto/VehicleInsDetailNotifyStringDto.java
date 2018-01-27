package com.xinyunlian.jinfu.zhongan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/3/14/0014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleInsDetailNotifyStringDto implements Serializable {
    private static final long serialVersionUID = -6046761927152279674L;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("insurePlace")
    private String insurePlace;

    @JsonProperty("vehicleLicencePlateNo")
    private String vehicleLicencePlateNo;

    @JsonProperty("vehicleVIN")
    private String vehicleVIN;

    @JsonProperty("vehicleEngineNo")
    private String vehicleEngineNo;

    @JsonProperty("VehicleBrand")
    private String vehicleBrand;

    @JsonProperty("policyHolderName")
    private String policyHolderName;

    @JsonProperty("policyHolderPhoneNo")
    private String policyHolderPhoneNo;

    @JsonProperty("vehiclePolicyOrderNo")
    private String vehiclePolicyOrderNo;

    @JsonProperty("isNetToTelemarket")
    private String netToTelemarket;

    @JsonProperty("recipienterName")
    private String recipienterName;

    @JsonProperty("recipienterPhone")
    private String recipienterPhone;

    @JsonProperty("recipienterAddress")
    private String recipienterAddress;

    @JsonProperty("taxPreimum")
    private Double taxPreimum;

    @JsonProperty("paymentWay")
    private String paymentWay;

    @JsonProperty("installmentNo")
    private String installmentNo;

    @JsonProperty("endorsementType")
    private String endorsementType;

    @JsonProperty("businessTpEndorsementNo")
    private String businessTpEndorsementNo;

    @JsonProperty("businessFee")
    private Double businessFee;

    @JsonProperty("businessApplyTime")
    private String businessApplyTime;

    @JsonProperty("businessEffectiveDate")
    private String businessEffectiveDate;

    @JsonProperty("compelTpEndorsementNo")
    private String compelTpEndorsementNo;

    @JsonProperty("compelFee")
    private Double compelFee;

    @JsonProperty("compelApplyTime")
    private String compelApplyTime;

    @JsonProperty("compelEndorEffectiveDate")
    private String compelEndorEffectiveDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInsurePlace() {
        return insurePlace;
    }

    public void setInsurePlace(String insurePlace) {
        this.insurePlace = insurePlace;
    }

    public String getVehicleLicencePlateNo() {
        return vehicleLicencePlateNo;
    }

    public void setVehicleLicencePlateNo(String vehicleLicencePlateNo) {
        this.vehicleLicencePlateNo = vehicleLicencePlateNo;
    }

    public String getVehicleVIN() {
        return vehicleVIN;
    }

    public void setVehicleVIN(String vehicleVIN) {
        this.vehicleVIN = vehicleVIN;
    }

    public String getVehicleEngineNo() {
        return vehicleEngineNo;
    }

    public void setVehicleEngineNo(String vehicleEngineNo) {
        this.vehicleEngineNo = vehicleEngineNo;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

    public String getPolicyHolderPhoneNo() {
        return policyHolderPhoneNo;
    }

    public void setPolicyHolderPhoneNo(String policyHolderPhoneNo) {
        this.policyHolderPhoneNo = policyHolderPhoneNo;
    }

    public String getVehiclePolicyOrderNo() {
        return vehiclePolicyOrderNo;
    }

    public void setVehiclePolicyOrderNo(String vehiclePolicyOrderNo) {
        this.vehiclePolicyOrderNo = vehiclePolicyOrderNo;
    }

    public String getNetToTelemarket() {
        return netToTelemarket;
    }

    public void setNetToTelemarket(String netToTelemarket) {
        this.netToTelemarket = netToTelemarket;
    }

    public String getRecipienterName() {
        return recipienterName;
    }

    public void setRecipienterName(String recipienterName) {
        this.recipienterName = recipienterName;
    }

    public String getRecipienterPhone() {
        return recipienterPhone;
    }

    public void setRecipienterPhone(String recipienterPhone) {
        this.recipienterPhone = recipienterPhone;
    }

    public String getRecipienterAddress() {
        return recipienterAddress;
    }

    public void setRecipienterAddress(String recipienterAddress) {
        this.recipienterAddress = recipienterAddress;
    }

    public Double getTaxPreimum() {
        return taxPreimum;
    }

    public void setTaxPreimum(Double taxPreimum) {
        this.taxPreimum = taxPreimum;
    }

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
    }

    public String getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(String installmentNo) {
        this.installmentNo = installmentNo;
    }

    public String getEndorsementType() {
        return endorsementType;
    }

    public void setEndorsementType(String endorsementType) {
        this.endorsementType = endorsementType;
    }

    public String getBusinessTpEndorsementNo() {
        return businessTpEndorsementNo;
    }

    public void setBusinessTpEndorsementNo(String businessTpEndorsementNo) {
        this.businessTpEndorsementNo = businessTpEndorsementNo;
    }

    public Double getBusinessFee() {
        return businessFee;
    }

    public void setBusinessFee(Double businessFee) {
        this.businessFee = businessFee;
    }

    public String getBusinessApplyTime() {
        return businessApplyTime;
    }

    public void setBusinessApplyTime(String businessApplyTime) {
        this.businessApplyTime = businessApplyTime;
    }

    public String getBusinessEffectiveDate() {
        return businessEffectiveDate;
    }

    public void setBusinessEffectiveDate(String businessEffectiveDate) {
        this.businessEffectiveDate = businessEffectiveDate;
    }

    public String getCompelTpEndorsementNo() {
        return compelTpEndorsementNo;
    }

    public void setCompelTpEndorsementNo(String compelTpEndorsementNo) {
        this.compelTpEndorsementNo = compelTpEndorsementNo;
    }

    public Double getCompelFee() {
        return compelFee;
    }

    public void setCompelFee(Double compelFee) {
        this.compelFee = compelFee;
    }

    public String getCompelApplyTime() {
        return compelApplyTime;
    }

    public void setCompelApplyTime(String compelApplyTime) {
        this.compelApplyTime = compelApplyTime;
    }

    public String getCompelEndorEffectiveDate() {
        return compelEndorEffectiveDate;
    }

    public void setCompelEndorEffectiveDate(String compelEndorEffectiveDate) {
        this.compelEndorEffectiveDate = compelEndorEffectiveDate;
    }
}
