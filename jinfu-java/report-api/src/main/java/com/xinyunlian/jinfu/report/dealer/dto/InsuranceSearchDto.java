package com.xinyunlian.jinfu.report.dealer.dto;

import com.xinyunlian.jinfu.report.dealer.enums.EInsuranceSource;
import com.xinyunlian.jinfu.report.dealer.enums.EInsuranceStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bright on 2016/11/29.
 */
public class InsuranceSearchDto implements Serializable {
    private String perInsuranceOrderNo;

    private EInsuranceStatus perInsOrderStatus;

    private String tobaccoPermiNo;

    private String phoneNo;

    private EInsuranceSource perInsDealSource;

    private String productId;

    private String insuredPerson;

    private String policyHolder;

    private String dealerPerson;

    private Date orderDateFrom;

    private Date orderDateTo;

    private String remark;

    private String storeAreaTreePath;

    public String getPerInsuranceOrderNo() {
        return perInsuranceOrderNo;
    }

    public void setPerInsuranceOrderNo(String perInsuranceOrderNo) {
        this.perInsuranceOrderNo = perInsuranceOrderNo;
    }

    public EInsuranceStatus getPerInsOrderStatus() {
        return perInsOrderStatus;
    }

    public void setPerInsOrderStatus(EInsuranceStatus perInsOrderStatus) {
        this.perInsOrderStatus = perInsOrderStatus;
    }

    public String getTobaccoPermiNo() {
        return tobaccoPermiNo;
    }

    public void setTobaccoPermiNo(String tobaccoPermiNo) {
        this.tobaccoPermiNo = tobaccoPermiNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public EInsuranceSource getPerInsDealSource() {
        return perInsDealSource;
    }

    public void setPerInsDealSource(EInsuranceSource perInsDealSource) {
        this.perInsDealSource = perInsDealSource;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getDealerPerson() {
        return dealerPerson;
    }

    public void setDealerPerson(String dealerPerson) {
        this.dealerPerson = dealerPerson;
    }

    public Date getOrderDateFrom() {
        return orderDateFrom;
    }

    public void setOrderDateFrom(Date orderDateFrom) {
        this.orderDateFrom = orderDateFrom;
    }

    public Date getOrderDateTo() {
        return orderDateTo;
    }

    public void setOrderDateTo(Date orderDateTo) {
        this.orderDateTo = orderDateTo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStoreAreaTreePath() {
        return storeAreaTreePath;
    }

    public void setStoreAreaTreePath(String storeAreaTreePath) {
        this.storeAreaTreePath = storeAreaTreePath;
    }
}
