package com.xinyunlian.jinfu.store.dto.resp;

import com.xinyunlian.jinfu.user.dto.UserInfoDto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by KimLL on 2016/8/18.
 */
public class StoreInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long storeId;

    private String storeName;

    private String userId;

    private String areaId;

    private String province;

    private String city;

    private String area;

    private String street;

    private String address;

    private String tobaccoCertificateNo;

    private String tobaccoCertificatePic;

    private String bizLicence;

    private String bizLicencePic;

    private Date tobaccoEndDate;

    private Date bizEndDate;

    private String qrCodeUrl;

    private UserInfoDto userInfoDto;

    private String industryMcc;//行业编号

    private String industryName;//行业名称

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

    public String getTobaccoCertificatePic() {
        return tobaccoCertificatePic;
    }

    public void setTobaccoCertificatePic(String tobaccoCertificatePic) {
        this.tobaccoCertificatePic = tobaccoCertificatePic;
    }

    public String getBizLicence() {
        return bizLicence;
    }

    public void setBizLicence(String bizLicence) {
        this.bizLicence = bizLicence;
    }

    public String getBizLicencePic() {
        return bizLicencePic;
    }

    public void setBizLicencePic(String bizLicencePic) {
        this.bizLicencePic = bizLicencePic;
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

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public UserInfoDto getUserInfoDto() {
        return userInfoDto;
    }

    public void setUserInfoDto(UserInfoDto userInfoDto) {
        this.userInfoDto = userInfoDto;
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
}
