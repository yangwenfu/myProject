package com.xinyunlian.jinfu.zhongan.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/3/14/0014.
 */
public class VehInsDetailNotifyDto implements Serializable {
    private static final long serialVersionUID = -4563352613835922119L;

    //金服店铺保订单号
    private String userId;

    //投保地
    private String insurePlace;

    //车牌号码
    private String vehicleLicencePlateNo;

    //车辆车架号
    private String vehicleVIN;

    //车辆发动机号
    private String vehicleEngineNo;

    //品牌型号
    private String vehicleBrand;

    //投保人姓名
    private String policyHolderName;

    //投保手机号码
    private String policyHolderPhoneNo;

    //订单号
    private String vehiclePolicyOrderNo;

    //电网销标志 0：网销；1：电销
    private String netToTelemarket;

    //保单收件人姓名
    private String recipienterName;

    //保单收件人手机号码
    private String recipienterPhone;

    //保单收件地址
    private String recipienterAddress;

    //车船税
    private Double taxPreimum;

    //支付方式
    private String paymentWay;

    //分期期数
    private String installmentNo;

    //批改类型
    private String endorsementType;

    //批单号(商业)
    private String businessTpEndorsementNo;

    //批改金额(商业)
    private Double businessFee;

    //批改日期(商业)
    private String businessApplyTime;

    //批改生效日期(商业)
    private String businessEffectiveDate;

    //批单号(交强)
    private String compelTpEndorsementNo;

    //批改金额(交强)
    private Double compelFee;

    //批改日期(交强)
    private String compelApplyTime;

    //批改生效日期(交强)
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
