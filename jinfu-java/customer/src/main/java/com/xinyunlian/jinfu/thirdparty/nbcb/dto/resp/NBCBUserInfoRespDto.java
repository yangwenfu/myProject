package com.xinyunlian.jinfu.thirdparty.nbcb.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by bright on 2017/5/17.
 */
public class NBCBUserInfoRespDto extends CommonRespDto {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("age")
    private String age;

    @JsonProperty("marry_status")
    private String marryStatus;

    @JsonProperty("id_no")
    private String idNo;

    @JsonProperty("similarity")
    private String similarity;

    @JsonProperty("mobile_no")
    private String mobileNo;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("house_property")
    private String houseProperty;

    @JsonProperty("address")
    private String address;

    @JsonProperty("linkmen")
    private String linkmen;

    @JsonProperty("store_name")
    private String storeName;

    @JsonProperty("tobacco_reg_date")
    private String tobaccoRegDate;

    @JsonProperty("tobacco_certificate_no")
    private String tobaccoCertificationNo;

    @JsonProperty("biz_manager")
    private String bizManager;

    @JsonProperty("biz_manager_tell")
    private String bizManagerTel;

    @JsonProperty("biz_address")
    private String bizAddress;

    @JsonProperty("biz_retail_format")
    private String bizRetailFormat;

    @JsonProperty("biz_scale")
    private String bizScale;

    @JsonProperty("user_pic")
    private String userPic;

    @JsonProperty("pre_credit_line")
    private String preCreditLine;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(String marryStatus) {
        this.marryStatus = marryStatus;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHouseProperty() {
        return houseProperty;
    }

    public void setHouseProperty(String houseProperty) {
        this.houseProperty = houseProperty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkmen() {
        return linkmen;
    }

    public void setLinkmen(String linkmen) {
        this.linkmen = linkmen;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTobaccoRegDate() {
        return tobaccoRegDate;
    }

    public void setTobaccoRegDate(String tobaccoRegDate) {
        this.tobaccoRegDate = tobaccoRegDate;
    }

    public String getTobaccoCertificationNo() {
        return tobaccoCertificationNo;
    }

    public void setTobaccoCertificationNo(String tobaccoCertificationNo) {
        this.tobaccoCertificationNo = tobaccoCertificationNo;
    }

    public String getBizManager() {
        return bizManager;
    }

    public void setBizManager(String bizManager) {
        this.bizManager = bizManager;
    }

    public String getBizManagerTel() {
        return bizManagerTel;
    }

    public void setBizManagerTel(String bizManagerTel) {
        this.bizManagerTel = bizManagerTel;
    }

    public String getBizAddress() {
        return bizAddress;
    }

    public void setBizAddress(String bizAddress) {
        this.bizAddress = bizAddress;
    }

    public String getBizRetailFormat() {
        return bizRetailFormat;
    }

    public void setBizRetailFormat(String bizRetailFormat) {
        this.bizRetailFormat = bizRetailFormat;
    }

    public String getBizScale() {
        return bizScale;
    }

    public void setBizScale(String bizScale) {
        this.bizScale = bizScale;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getPreCreditLine() {
        return preCreditLine;
    }

    public void setPreCreditLine(String preCreditLine) {
        this.preCreditLine = preCreditLine;
    }
}
