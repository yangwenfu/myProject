package com.xinyunlian.jinfu.insurance.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealSource;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealType;
import com.xinyunlian.jinfu.insurance.enums.EPerInsOrderStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by DongFC on 2016-09-22.
 */
public class PerInsInfoSearchDto extends PagingDto<PerInsInfoSearchDto> {
    private static final long serialVersionUID = -2742780352886234722L;

    private String perInsuranceOrderNo;

    private Long storeId;

    private String productId;

    private EPerInsOrderStatus perInsOrderStatus;

    private List<String> perInsuranceOrderNoList;

    private String operatorName;

    private String storeName;

    private EPerInsDealType dealType;

    private String storeAreaTreePath;

    private Date orderDateFrom;

    private Date orderDateTo;

    private List<String> tobaccoPermiNoList;

    private String tobaccoPermiNo;

    private String phoneNo;

    private EPerInsDealSource perInsDealSource;

    private String provinceId;

    private String cityId;

    private String countyId;

    private String streetId;

    private String insuredPerson;

    private String policyHolder;

    private String dealerPerson;

    private String remark;

    private List<Long> storeIdList;

    public EPerInsOrderStatus getPerInsOrderStatus() {
        return perInsOrderStatus;
    }

    public void setPerInsOrderStatus(EPerInsOrderStatus perInsOrderStatus) {
        this.perInsOrderStatus = perInsOrderStatus;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public EPerInsDealType getDealType() {
        return dealType;
    }

    public void setDealType(EPerInsDealType dealType) {
        this.dealType = dealType;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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

    public String getPerInsuranceOrderNo() {
        return perInsuranceOrderNo;
    }

    public void setPerInsuranceOrderNo(String perInsuranceOrderNo) {
        this.perInsuranceOrderNo = perInsuranceOrderNo;
    }

    public List<String> getPerInsuranceOrderNoList() {
        return perInsuranceOrderNoList;
    }

    public void setPerInsuranceOrderNoList(List<String> perInsuranceOrderNoList) {
        this.perInsuranceOrderNoList = perInsuranceOrderNoList;
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

    public List<String> getTobaccoPermiNoList() {
        return tobaccoPermiNoList;
    }

    public void setTobaccoPermiNoList(List<String> tobaccoPermiNoList) {
        this.tobaccoPermiNoList = tobaccoPermiNoList;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getTobaccoPermiNo() {
        return tobaccoPermiNo;
    }

    public void setTobaccoPermiNo(String tobaccoPermiNo) {
        this.tobaccoPermiNo = tobaccoPermiNo;
    }

    public EPerInsDealSource getPerInsDealSource() {
        return perInsDealSource;
    }

    public void setPerInsDealSource(EPerInsDealSource perInsDealSource) {
        this.perInsDealSource = perInsDealSource;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Long> getStoreIdList() {
        return storeIdList;
    }

    public void setStoreIdList(List<Long> storeIdList) {
        this.storeIdList = storeIdList;
    }
}
