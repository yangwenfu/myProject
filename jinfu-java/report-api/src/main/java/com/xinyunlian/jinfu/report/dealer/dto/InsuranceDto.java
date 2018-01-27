package com.xinyunlian.jinfu.report.dealer.dto;

import com.xinyunlian.jinfu.report.dealer.enums.EDealType;
import com.xinyunlian.jinfu.report.dealer.enums.EInsuranceSource;
import com.xinyunlian.jinfu.report.dealer.enums.EInsuranceStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by bright on 2016/11/29.
 */
public class InsuranceDto implements Serializable {

    private String userId;

    private String orderNo;

    private Date orderDate;

    private String productName;

    private String tobaccoPermiNo;

    private String storeName;

    private String province;

    private String city;

    private String area;

    private String street;

    private String fullAddress;

    private String userName;

    private String phoneNo;

    private BigDecimal insuranceAmt;

    private BigDecimal insuranceFee;

    private BigDecimal businessPremiumInterval;

    private BigDecimal compelPremiumInterval;

    private Date orderStartTime;

    private Date orderStopTime;

    private EInsuranceStatus orderStatus;

    private EDealType dealType;

    private EInsuranceSource dealSource;

    private String dealerUserName;

    private String dealerUserMobile;

    private String dealerName;

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
}
