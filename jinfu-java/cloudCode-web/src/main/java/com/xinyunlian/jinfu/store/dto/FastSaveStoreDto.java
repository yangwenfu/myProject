package com.xinyunlian.jinfu.store.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by menglei on 2017/8/30.
 */
public class FastSaveStoreDto implements Serializable {

    @NotBlank(message = "所属行业不能为空")
    private String industryMcc;//行业

    @NotBlank(message = "店铺名称不能为空")
    private String storeName;

    @NotBlank(message = "所在地区不能为空")
    private String districtId;

    @NotBlank(message = "所在地区不能为空")
    private String provinceId;

    @NotBlank(message = "所在地区不能为空")
    private String cityId;

    @NotBlank(message = "所在地区不能为空")
    private String areaId;

    private String streetId;

    @NotBlank(message = "所在地区不能为空")
    private String province;

    @NotBlank(message = "所在地区不能为空")
    private String city;

    @NotBlank(message = "所在地区不能为空")
    private String area;

    private String street;

    @NotBlank(message = "详细地址不能为空")
    private String address;

    private String tobaccoCertificateNo;

    @NotBlank(message = "营业执照号不能为空")
    private String bizLicence;

    @NotBlank(message = "营业执照号图片不能为空")
    private String bizLicencePicBase64;

    private String licence;//许可证

    @NotBlank(message = "门头照不能为空")
    private String storeHeaderPicBase64;//门头照

    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    public String getIndustryMcc() {
        return industryMcc;
    }

    public void setIndustryMcc(String industryMcc) {
        this.industryMcc = industryMcc;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
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

    public String getBizLicencePicBase64() {
        return bizLicencePicBase64;
    }

    public void setBizLicencePicBase64(String bizLicencePicBase64) {
        this.bizLicencePicBase64 = bizLicencePicBase64;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getStoreHeaderPicBase64() {
        return storeHeaderPicBase64;
    }

    public void setStoreHeaderPicBase64(String storeHeaderPicBase64) {
        this.storeHeaderPicBase64 = storeHeaderPicBase64;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
