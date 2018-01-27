package com.xinyunlian.jinfu.dealer.store.dto;

import com.xinyunlian.jinfu.dealer.dto.DealerStoreDto;
import com.xinyunlian.jinfu.user.enums.ESource;

import java.io.Serializable;
import java.util.List;

/**
 * Created by menglei on 2016/9/18.
 */
public class StoreDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long storeId;

    private String storeName;

    private String userId;

    private String provinceId;

    private String cityId;

    private String districtId;

    private String streetId;

    private String areaId;

    private String province;

    private String city;

    private String area;

    private String street;

    private String address;

    private ESource source;

    private String createTime;

    private String qrCodeUrl;

    private List<DealerStoreDto> dealerStoreList;

    private String industryMcc;//行业编号

    private String industryName;//行业名称

    private String licence;//许可证

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public ESource getSource() {
        return source;
    }

    public void setSource(ESource source) {
        this.source = source;
    }

    public List<DealerStoreDto> getDealerStoreList() {
        return dealerStoreList;
    }

    public void setDealerStoreList(List<DealerStoreDto> dealerStoreList) {
        this.dealerStoreList = dealerStoreList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getIndustryMcc() {
        return industryMcc;
    }

    public void setIndustryMcc(String industryMcc) {
        this.industryMcc = industryMcc;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }
}
