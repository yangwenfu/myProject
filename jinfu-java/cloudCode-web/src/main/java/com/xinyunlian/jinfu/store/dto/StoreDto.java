package com.xinyunlian.jinfu.store.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2017/1/5.
 */
public class StoreDto implements Serializable {
    
    private Long storeId;

    private String qrCodeNo;

    private String qrCodeUrl;

    private Long bankCardId;//银行卡，对公账号

    private String userId;

    private String storeName;

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

    private String bankCardPic;//银行卡照，对公账号许可证

    private String tobaccoCertificatePicBase64;

    private String bizLicencePicBase64;

    private String storeHeaderPicBase64;//门头照

    private String storeInnerPicBase64;//店内照

    private String bankCardPicBase64;//银行卡照，对公账号许可证

    private Date bizEndDate;//营业执照到期期

    private String idCardFrontPicBase64;//身份证正面

    private String idCardBackPicBase64;//身份证反面

    private String industryMcc;//行业

    private String licence;//许可证

    private String licencePicBase64;//许可证图片

    private Integer bankCardType;//银行卡类型 1=银行卡 2=对公账号许可证

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreHeaderPicBase64() {
        return storeHeaderPicBase64;
    }

    public void setStoreHeaderPicBase64(String storeHeaderPicBase64) {
        this.storeHeaderPicBase64 = storeHeaderPicBase64;
    }

    public String getBankCardPic() {
        return bankCardPic;
    }

    public void setBankCardPic(String bankCardPic) {
        this.bankCardPic = bankCardPic;
    }

    public String getBankCardPicBase64() {
        return bankCardPicBase64;
    }

    public void setBankCardPicBase64(String bankCardPicBase64) {
        this.bankCardPicBase64 = bankCardPicBase64;
    }

    public Date getBizEndDate() {
        return bizEndDate;
    }

    public void setBizEndDate(Date bizEndDate) {
        this.bizEndDate = bizEndDate;
    }

    public String getIdCardFrontPicBase64() {
        return idCardFrontPicBase64;
    }

    public void setIdCardFrontPicBase64(String idCardFrontPicBase64) {
        this.idCardFrontPicBase64 = idCardFrontPicBase64;
    }

    public String getIdCardBackPicBase64() {
        return idCardBackPicBase64;
    }

    public void setIdCardBackPicBase64(String idCardBackPicBase64) {
        this.idCardBackPicBase64 = idCardBackPicBase64;
    }

    public String getStoreInnerPicBase64() {
        return storeInnerPicBase64;
    }

    public void setStoreInnerPicBase64(String storeInnerPicBase64) {
        this.storeInnerPicBase64 = storeInnerPicBase64;
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

    public Integer getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(Integer bankCardType) {
        this.bankCardType = bankCardType;
    }
}
