package com.xinyunlian.jinfu.user.dto;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by KimLL on 2016/8/18.
 */
public class StoreActiveDto implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long storeId;

    @NotEmpty
    private String storeName = "";

    private String userId = "";

    @NotEmpty
    private String provinceId = "";

    @NotEmpty
    private String cityId = "";

    private String districtId = "";

    private String streetId = "";

    @NotEmpty
    private String areaId = "";

    @NotEmpty
    private String province = "";

    @NotEmpty
    private String city = "";

    @NotEmpty
    private String area = "";

    private String street = "";

    @NotEmpty
    private String address = "";

    @NotEmpty
    private String tobaccoCertificateNo = "";

    private String bizLicence = "";

    private Date tobaccoEndDate;

    private Date bizEndDate;

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
}
