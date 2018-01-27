package com.xinyunlian.jinfu.insurance.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.insurance.enums.converters.EPerInsDealSourceConverter;
import com.xinyunlian.jinfu.insurance.enums.converters.EPerInsDealTypeConverter;
import com.xinyunlian.jinfu.insurance.enums.converters.EPerInsOrderStatusConverter;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealSource;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealType;
import com.xinyunlian.jinfu.insurance.enums.EPerInsOrderStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by DongFC on 2016-09-21.
 */
@Entity
@Table(name = "per_insurance_info")
@EntityListeners(IdInjectionEntityListener.class)
public class PerInsuranceInfoPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 2992719360758348399L;

    @Id
    @Column(name = "PER_INSURANCE_ORDER_NO")
    private String perInsuranceOrderNo;

    @Column(name = "OPERATOR_NAME")
    private String operatorName;

    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Column(name = "TOBACCO_PERMI_NO")
    private String tobaccoPermiNo;

    @Column(name = "STORE_AREA_TREE_PATH")
    private String storeAreaTreePath;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "STORE_NAME")
    private String storeName;

    @Column(name = "STORE_ADDRESS")
    private String storeAddress;

    @Column(name = "ORDER_START_TIME")
    private Date orderStartTime;

    @Column(name = "ORDER_STOP_TIME")
    private Date orderStopTime;

    @Column(name = "PHONE_NO")
    private String phoneNo;

    @Column(name = "POLICY_HOLDER")
    private String policyHolder;

    @Column(name = "INSURED_PERSON")
    private String insuredPerson;

    @Column(name = "INSURANCE_AMT")
    private Long insuranceAmt;

    @Column(name = "INSURANCE_FEE")
    private Long insuranceFee;

    @Column(name = "ORDER_URL")
    private String orderUrl;

    @Column(name = "ORDER_STATUS")
    @Convert(converter = EPerInsOrderStatusConverter.class)
    private EPerInsOrderStatus orderStatus;

    @Column(name = "ORDER_STATUS_CAUSE")
    private String orderStatusCause;

    @Column(name = "PRODUCT_ID")
    private String productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "DEAL_TYPE")
    @Convert(converter = EPerInsDealTypeConverter.class)
    private EPerInsDealType dealType;

    @Column(name = "DEAL_SOURCE")
    @Convert(converter = EPerInsDealSourceConverter.class)
    private EPerInsDealSource dealSource;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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

    public String getStoreAreaTreePath() {
        return storeAreaTreePath;
    }

    public void setStoreAreaTreePath(String storeAreaTreePath) {
        this.storeAreaTreePath = storeAreaTreePath;
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

    public EPerInsOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(EPerInsOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPerInsuranceOrderNo() {
        return perInsuranceOrderNo;
    }

    public void setPerInsuranceOrderNo(String perInsuranceOrderNo) {
        this.perInsuranceOrderNo = perInsuranceOrderNo;
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

    public String getOrderStatusCause() {
        return orderStatusCause;
    }

    public void setOrderStatusCause(String orderStatusCause) {
        this.orderStatusCause = orderStatusCause;
    }
}
