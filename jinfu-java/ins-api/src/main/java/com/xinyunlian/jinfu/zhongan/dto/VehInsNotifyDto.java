package com.xinyunlian.jinfu.zhongan.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/3/14/0014.
 */
public class VehInsNotifyDto implements Serializable {
    private static final long serialVersionUID = -6004783616893269120L;

    //通知类型 1：投保，2：批改，3：退保
    private String notifyType;

    //众安保单号
    private String policyNo;

    //保额，单位元
    private Double sumInsured;

    //保费，单位元
    private Double premium;

    //保单起期
    private String effectiveDate;

    //保单止期
    private String expiryDate;

    //产品名称
    private String productName;

    //推广位code
    private String promoteCode;

    //推广位名称
    private String promoteName;

    //推广费
    private Double promoteFee;

    //车险保单类型 1：商业，2：交强
    private String vehicleType;

    private VehInsDetailNotifyDto vehInsDetailNotifyDto;

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

    public VehInsDetailNotifyDto getVehInsDetailNotifyDto() {
        return vehInsDetailNotifyDto;
    }

    public void setVehInsDetailNotifyDto(VehInsDetailNotifyDto vehInsDetailNotifyDto) {
        this.vehInsDetailNotifyDto = vehInsDetailNotifyDto;
    }
}
