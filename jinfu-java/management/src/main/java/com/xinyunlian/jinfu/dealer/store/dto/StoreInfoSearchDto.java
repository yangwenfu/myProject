package com.xinyunlian.jinfu.dealer.store.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.user.enums.ESource;

/**
 * Created by menglei on 2016/9/1.
 */
public class StoreInfoSearchDto extends PagingDto<StoreDto> {
    private static final long serialVersionUID = 1L;

    private Long storeId;

    private String userId;

    private String districtId;

    private String storeName;

    private String districtIds;

    private String province;

    private String city;

    private String area;

    private String street;

    private String fullAddress;

    private String address;

    private String tobaccoCertificateNo;

    private String mobile;

    private String userName;

    private String storeIds;

    private String dealerName;

    private ESource source;

    private String CreateStartDate;

    private String CreateEndDate;

    private String industryMcc;//行业编号

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

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictIds() {
        return districtIds;
    }

    public void setDistrictIds(String districtIds) {
        this.districtIds = districtIds;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(String storeIds) {
        this.storeIds = storeIds;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public ESource getSource() {
        return source;
    }

    public void setSource(ESource source) {
        this.source = source;
    }

    public String getCreateStartDate() {
        return CreateStartDate;
    }

    public void setCreateStartDate(String createStartDate) {
        CreateStartDate = createStartDate;
    }

    public String getCreateEndDate() {
        return CreateEndDate;
    }

    public void setCreateEndDate(String createEndDate) {
        CreateEndDate = createEndDate;
    }

    public String getIndustryMcc() {
        return industryMcc;
    }

    public void setIndustryMcc(String industryMcc) {
        this.industryMcc = industryMcc;
    }
}
