package com.xinyunlian.jinfu.external.entity;

import com.xinyunlian.jinfu.external.enums.converter.EApplOutTypeEnumConverter;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by godslhand on 2017/7/3.
 */
@Entity
@Table(name = "FP_LOAN_OUT_RISK")
public class LoanApplOutRiskPo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "APPLY_ID")
    private String applyId;

    @Column(name = "MERCHANT_LEVEL")
    private Integer merchantLevel;//店铺档位

    @Column(name = "MERCHANT_TYPE")
    private Integer merchantType;//店铺类型

    @Column(name = "TARGET_MARKET_TYPE")
    private Integer targetMarketType; //市场类型

    @Column(name = "CUSTOMER_TYPE")
    private Integer customerType;//用户类型

    @Column(name = "OWNER_COMPANY_NAME")
    private String ownerCompanyName;//所属公司

    @Column(name = "OPERATION_YEARS")
    private Integer operationYears;//经营年限
    @Column(name = "ACTIVE_MONTHS_IN_LAST_YEAR")
    private Integer activeMonthsInLastYear;//近１２个月内有订烟的月份数
    @Column(name = "ACTIVE_MONTHS_IN_LAST_QUARTER")
    private Integer activeMonthsInLastQuarter;//近3个月内有订烟的月份数
    @Column(name = "AVERAGE_MONTHLY_AMOUNT")
    private Double averageMonthlyAmount;//月均订烟额
    @Column(name = "VARIANCE")
    private Double variance;//订烟额波动率
    @Column(name = "ACTIVE_DEGREE")
    private Double activeDegree;//最近月份的订烟活跃度
    @Column(name = "SAME_OWNER_FLAG")
    private Integer sameOwnerFlag;//申请人是否与烟草系统中的负责人一致
    @Column(name = "SAME_CONTACT_FLAG")
    private Integer sameContactFlag;//申请人是否与烟草系统中的负责人联系电话一致
    @Column(name = "SAME_ARRESS_FLAG")
    private Integer sameArressFlag;//经营地址是否与烟草系统中的店铺地址一致

    @Column(name = "STYPE")
    @Convert(converter = EApplOutTypeEnumConverter.class)
    private EApplOutType type;

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

    public EApplOutType getType() {
        return type;
    }

    public void setType(EApplOutType type) {
        this.type = type;
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
