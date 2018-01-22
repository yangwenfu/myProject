package com.xinyunlian.jinfu.external.dto;

import com.xinyunlian.jinfu.loan.enums.EApplOutType;

import java.io.Serializable;

/**
 * @author willwang
 */
public class LoanOutRiskDto implements Serializable{

    private String id;

    private String userId;

    private String applyId;

    private Integer merchantLevel;//店铺档位

    private Integer merchantType;//店铺类型

    private Integer targetMarketType; //市场类型

    private Integer customerType;//用户类型

    private String ownerCompanyName;//所属公司


    private Integer operationYears;//经营年限
    private Integer activeMonthsInLastYear;//近１２个月内有订烟的月份数
    private Integer activeMonthsInLastQuarter;//近3个月内有订烟的月份数
    private Double averageMonthlyAmount;//月均订烟额
    private Double variance;//订烟额波动率
    private Double activeDegree;//最近月份的订烟活跃度
    private Integer sameOwnerFlag;//申请人是否与烟草系统中的负责人一致
    private Integer sameContactFlag;//申请人是否与烟草系统中的负责人联系电话一致
    private Integer sameArressFlag;//经营地址是否与烟草系统中的店铺地址一致

    private EApplOutType type;

    public EApplOutType getType() {
        return type;
    }

    public void setType(EApplOutType type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMerchantLevel() {
        return merchantLevel;
    }

    public void setMerchantLevel(Integer merchantLevel) {
        this.merchantLevel = merchantLevel;
    }

    public Integer getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(Integer merchantType) {
        this.merchantType = merchantType;
    }

    public Integer getTargetMarketType() {
        return targetMarketType;
    }

    public void setTargetMarketType(Integer targetMarketType) {
        this.targetMarketType = targetMarketType;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getOwnerCompanyName() {
        return ownerCompanyName;
    }

    public void setOwnerCompanyName(String ownerCompanyName) {
        this.ownerCompanyName = ownerCompanyName;
    }

    public Integer getOperationYears() {
        return operationYears;
    }

    public void setOperationYears(Integer operationYears) {
        this.operationYears = operationYears;
    }

    public Integer getActiveMonthsInLastYear() {
        return activeMonthsInLastYear;
    }

    public void setActiveMonthsInLastYear(Integer activeMonthsInLastYear) {
        this.activeMonthsInLastYear = activeMonthsInLastYear;
    }

    public Integer getActiveMonthsInLastQuarter() {
        return activeMonthsInLastQuarter;
    }

    public void setActiveMonthsInLastQuarter(Integer activeMonthsInLastQuarter) {
        this.activeMonthsInLastQuarter = activeMonthsInLastQuarter;
    }

    public Double getAverageMonthlyAmount() {
        return averageMonthlyAmount;
    }

    public void setAverageMonthlyAmount(Double averageMonthlyAmount) {
        this.averageMonthlyAmount = averageMonthlyAmount;
    }

    public Double getVariance() {
        return variance;
    }

    public void setVariance(Double variance) {
        this.variance = variance;
    }

    public Double getActiveDegree() {
        return activeDegree;
    }

    public void setActiveDegree(Double activeDegree) {
        this.activeDegree = activeDegree;
    }

    public Integer getSameOwnerFlag() {
        return sameOwnerFlag;
    }

    public void setSameOwnerFlag(Integer sameOwnerFlag) {
        this.sameOwnerFlag = sameOwnerFlag;
    }

    public Integer getSameContactFlag() {
        return sameContactFlag;
    }

    public void setSameContactFlag(Integer sameContactFlag) {
        this.sameContactFlag = sameContactFlag;
    }

    public Integer getSameArressFlag() {
        return sameArressFlag;
    }

    public void setSameArressFlag(Integer sameArressFlag) {
        this.sameArressFlag = sameArressFlag;
    }


}
