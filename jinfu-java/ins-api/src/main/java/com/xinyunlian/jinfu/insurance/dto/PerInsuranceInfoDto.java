package com.xinyunlian.jinfu.insurance.dto;

import com.xinyunlian.jinfu.insurance.enums.EPerInsDealSource;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealType;
import com.xinyunlian.jinfu.insurance.enums.EPerInsOrderStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DongFC on 2016-09-21.
 */
public class PerInsuranceInfoDto implements Serializable {
    private static final long serialVersionUID = -6375635388364584073L;

    private String perInsuranceOrderNo;

    private String operatorName;

    private Date orderDate;

    private String tobaccoPermiNo;

    private String storeAreaTreePath;

    private Long storeId;

    private String storeName;

    private String storeAddress;

    private Date orderStartTime;

    private Date orderStopTime;

    private String phoneNo;

    private String policyHolder;

    private String insuredPerson;

    private Long insuranceAmt;

    private Long insuranceFee;

    private String orderUrl;

    private EPerInsOrderStatus orderStatus;

    private String orderStatusCause;

    private String productId;

    private String productName;

    private String remark;

    private EPerInsDealType dealType;

    private EPerInsDealSource dealSource;

    private String orderStatusName;

    private String dealTypeName;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public EPerInsDealSource getDealSource() {
        return dealSource;
    }

    public void setDealSource(EPerInsDealSource dealSource) {
        this.dealSource = dealSource;
    }

    public EPerInsDealType getDealType() {
        return dealType;
    }

    public void setDealType(EPerInsDealType dealType) {
        this.dealType = dealType;
    }

    public Long getInsuranceAmt() {
        return insuranceAmt;
    }

    public void setInsuranceAmt(Long insuranceAmt) {
        this.insuranceAmt = insuranceAmt;
    }

    public Long getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(Long insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(Date orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public EPerInsOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(EPerInsOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderStopTime() {
        return orderStopTime;
    }

    public void setOrderStopTime(Date orderStopTime) {
        this.orderStopTime = orderStopTime;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public String getPerInsuranceOrderNo() {
        return perInsuranceOrderNo;
    }

    public void setPerInsuranceOrderNo(String perInsuranceOrderNo) {
        this.perInsuranceOrderNo = perInsuranceOrderNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreAreaTreePath() {
        return storeAreaTreePath;
    }

    public void setStoreAreaTreePath(String storeAreaTreePath) {
        this.storeAreaTreePath = storeAreaTreePath;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTobaccoPermiNo() {
        return tobaccoPermiNo;
    }

    public void setTobaccoPermiNo(String tobaccoPermiNo) {
        this.tobaccoPermiNo = tobaccoPermiNo;
    }

    public String getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(String insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public String getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(String policyHolder) {
        this.policyHolder = policyHolder;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getDealTypeName() {
        return dealTypeName;
    }

    public void setDealTypeName(String dealTypeName) {
        this.dealTypeName = dealTypeName;
    }

    public String getOrderStatusCause() {
        return orderStatusCause;
    }

    public void setOrderStatusCause(String orderStatusCause) {
        this.orderStatusCause = orderStatusCause;
    }
}
