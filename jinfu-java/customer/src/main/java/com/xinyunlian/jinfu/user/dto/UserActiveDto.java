package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.common.security.authc.ESourceType;

/**
 * Created by dell1 on 2016/11/3.
 */
public class UserActiveDto {

    private String mobile;

    private String password;

    private String access;

    private String verifyCode;

    private String provinceId;

    private String cityId;

    private String streetId;

    private String areaId;

    private String province;

    private String city;

    private String area;

    private String street;

    private ESourceType sourceType;

    private String storeName;

    private String tobaccoCertificateNo;

    private String address;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
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

    public ESourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(ESourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserActiveDto{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", access='" + access + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", streetId='" + streetId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", street='" + street + '\'' +
                ", sourceType=" + sourceType +
                ", storeName='" + storeName + '\'' +
                ", tobaccoCertificateNo='" + tobaccoCertificateNo + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
