package com.xinyunlian.jinfu.report.dealer.entity;

import com.xinyunlian.jinfu.report.dealer.enums.EDealType;
import com.xinyunlian.jinfu.report.dealer.enums.EInsuranceSource;
import com.xinyunlian.jinfu.report.dealer.enums.EInsuranceStatus;
import com.xinyunlian.jinfu.report.dealer.enums.converter.EDealTypeEnumConverter;
import com.xinyunlian.jinfu.report.dealer.enums.converter.EInsuranceSourceEnumConverter;
import com.xinyunlian.jinfu.report.dealer.enums.converter.EInsuranceStatusEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by bright on 2016/11/29.
 */
@Entity
@Table(name = "insurance")
public class InsurancePo {

    @Column(name = "USER_ID")
    private String userId;

    @Id
    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "tobacco_permi_no")
    private String tobaccoPermiNo;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "STORE_AREA_TREE_PATH")
    private String storeAreaTreePath;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "area")
    private String area;

    @Column(name = "street")
    private String street;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "insurance_amt")
    private BigDecimal insuranceAmt;

    @Column(name = "insurance_fee")
    private BigDecimal insuranceFee;

    @Column(name = "order_start_time")
    private Date orderStartTime;

    @Column(name = "order_stop_time")
    private Date orderStopTime;

    @Column(name = "order_status")
    @Convert(converter = EInsuranceStatusEnumConverter.class)
    private EInsuranceStatus orderStatus;

    @Column(name = "deal_type")
    @Convert(converter = EDealTypeEnumConverter.class)
    private EDealType dealType;

    @Column(name = "deal_source")
    @Convert(converter = EInsuranceSourceEnumConverter.class)
    private EInsuranceSource dealSource;

    @Column(name = "dealer_user_name")
    private String dealerUserName;

    @Column(name = "dealer_user_mobile")
    private String dealerUserMobile;

    @Column(name = "dealer_name")
    private String dealerName;

    @Column(name = "remark")
    private String remark;

    @Column(name = "policy_holder")
    private String policyHolder;

    @Column(name = "insured_person")
    private String insuredPerson;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "BUSINESS_PREMIUM_INTERVAL")
    private BigDecimal businessPremiumInterval;

    @Column(name = "COMPEL_PREMIUM_INTERVAL")
    private BigDecimal compelPremiumInterval;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTobaccoPermiNo() {
        return tobaccoPermiNo;
    }

    public void setTobaccoPermiNo(String tobaccoPermiNo) {
        this.tobaccoPermiNo = tobaccoPermiNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public BigDecimal getInsuranceAmt() {
        return insuranceAmt;
    }

    public void setInsuranceAmt(BigDecimal insuranceAmt) {
        this.insuranceAmt = insuranceAmt;
    }

    public BigDecimal getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(BigDecimal insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public Date getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(Date orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public Date getOrderStopTime() {
        return orderStopTime;
    }

    public void setOrderStopTime(Date orderStopTime) {
        this.orderStopTime = orderStopTime;
    }

    public EInsuranceStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(EInsuranceStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public EDealType getDealType() {
        return dealType;
    }

    public void setDealType(EDealType dealType) {
        this.dealType = dealType;
    }

    public EInsuranceSource getDealSource() {
        return dealSource;
    }

    public void setDealSource(EInsuranceSource dealSource) {
        this.dealSource = dealSource;
    }

    public String getDealerUserName() {
        return dealerUserName;
    }

    public void setDealerUserName(String dealerUserName) {
        this.dealerUserName = dealerUserName;
    }

    public String getDealerUserMobile() {
        return dealerUserMobile;
    }

    public void setDealerUserMobile(String dealerUserMobile) {
        this.dealerUserMobile = dealerUserMobile;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(String policyHolder) {
        this.policyHolder = policyHolder;
    }

    public String getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(String insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStoreAreaTreePath() {
        return storeAreaTreePath;
    }

    public void setStoreAreaTreePath(String storeAreaTreePath) {
        this.storeAreaTreePath = storeAreaTreePath;
    }

    public BigDecimal getBusinessPremiumInterval() {
        return businessPremiumInterval;
    }

    public void setBusinessPremiumInterval(BigDecimal businessPremiumInterval) {
        this.businessPremiumInterval = businessPremiumInterval;
    }

    public BigDecimal getCompelPremiumInterval() {
        return compelPremiumInterval;
    }

    public void setCompelPremiumInterval(BigDecimal compelPremiumInterval) {
        this.compelPremiumInterval = compelPremiumInterval;
    }
}
