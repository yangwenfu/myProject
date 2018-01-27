package com.xinyunlian.jinfu.store.dto.req;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2016年09月05日.
 */
public class StoreDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long storeId;

    private String storeName;

    private String userId;

    private String districtId;

    private String provinceId;

    private String cityId;

    private String areaId;

    private String streetId;

    private String province;

    private String city;

    private String area;

    private String street;

    private String address;

    private String tobaccoCertificateNo;

    private String bizLicence;

    private Date tobaccoEndDate;

    private Date bizEndDate;

    private String tobaccoCertificatePicBase64;

    private String bizLicencePicBase64;

    private String logLng;

    private String logLat;

    private String logAddress;

    private String industryMcc;//行业

    private String licence;//许可证

    private String licencePicBase64;//许可证图片

    private Long storeWhiteListId;//白名单店铺id

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

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }

    public String getBizLicence() {
        return bizLicence;
    }

    public void setBizLicence(String bizLicence) {
        this.bizLicence = bizLicence;
    }

    public Date getTobaccoEndDate() {
        return tobaccoEndDate;
    }

    public void setTobaccoEndDate(Date tobaccoEndDate) {
        this.tobaccoEndDate = tobaccoEndDate;
    }

    public Date getBizEndDate() {
        return bizEndDate;
    }

    public void setBizEndDate(Date bizEndDate) {
        this.bizEndDate = bizEndDate;
    }

    public String getTobaccoCertificatePicBase64() {
        return tobaccoCertificatePicBase64;
    }

    public void setTobaccoCertificatePicBase64(String tobaccoCertificatePicBase64) {
        this.tobaccoCertificatePicBase64 = tobaccoCertificatePicBase64;
    }

    public String getBizLicencePicBase64() {
        return bizLicencePicBase64;
    }

    public void setBizLicencePicBase64(String bizLicencePicBase64) {
        this.bizLicencePicBase64 = bizLicencePicBase64;
    }

    public String getLogAddress() {
        return logAddress;
    }

    public void setLogAddress(String logAddress) {
        this.logAddress = logAddress;
    }

    public String getLogLng() {
        return logLng;
    }

    public void setLogLng(String logLng) {
        this.logLng = logLng;
    }

    public String getLogLat() {
        return logLat;
    }

    public void setLogLat(String logLat) {
        this.logLat = logLat;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
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

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getIndustryMcc() {
        return industryMcc;
    }

    public void setIndustryMcc(String industryMcc) {
        this.industryMcc = industryMcc;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getLicencePicBase64() {
        return licencePicBase64;
    }

    public void setLicencePicBase64(String licencePicBase64) {
        this.licencePicBase64 = licencePicBase64;
    }

    public Long getStoreWhiteListId() {
        return storeWhiteListId;
    }

    public void setStoreWhiteListId(Long storeWhiteListId) {
        this.storeWhiteListId = storeWhiteListId;
    }
}
