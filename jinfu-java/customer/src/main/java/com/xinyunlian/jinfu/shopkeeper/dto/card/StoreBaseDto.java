package com.xinyunlian.jinfu.shopkeeper.dto.card;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by King on 2017/2/14.
 */
public class StoreBaseDto implements Serializable{
    private static final long serialVersionUID = 6195700300435990670L;

    private String userId;

    private Long storeId;

    @NotEmpty
    private String storeName;

    @NotEmpty
    private String provinceId;

    @NotEmpty
    private String cityId;

    @NotEmpty
    private String districtId;

    @NotEmpty
    private String streetId;

    @NotEmpty
    private String areaId;

    @NotEmpty
    private String province;

    @NotEmpty
    private String city;

    @NotEmpty
    private String area;

    @NotEmpty
    private String street;

    @NotEmpty
    private String address;

    private String tobaccoCertificateNo;

    @NotEmpty
    private String bizLicence;

    private Boolean storeLicence;

    private String licenceName;

    private String industryMcc;

    private String indName;

    private String licence;

    /**
     * 店铺许可证照片
     */
    private String storeLicencePic;

    /**
     * 店铺许可证照片ID
     */
    private Long storeLicencePicId;

    /**
     * 店铺烟草证号
     */
    //@NotEmpty
    private String storeTobaccoPic;

    /**
     * 店铺烟草证号ID
     */
    private Long storeTobaccoPicId;

    /**
     * 许可证号图片
     */
    private String licencePic;

    /**
     * 许可证号ID
     */
    private Long licencePicId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getStoreLicencePic() {
        return storeLicencePic;
    }

    public void setStoreLicencePic(String storeLicencePic) {
        this.storeLicencePic = storeLicencePic;
    }

    public Long getStoreLicencePicId() {
        return storeLicencePicId;
    }

    public void setStoreLicencePicId(Long storeLicencePicId) {
        this.storeLicencePicId = storeLicencePicId;
    }

    public String getStoreTobaccoPic() {
        return storeTobaccoPic;
    }

    public void setStoreTobaccoPic(String storeTobaccoPic) {
        this.storeTobaccoPic = storeTobaccoPic;
    }

    public Long getStoreTobaccoPicId() {
        return storeTobaccoPicId;
    }

    public void setStoreTobaccoPicId(Long storeTobaccoPicId) {
        this.storeTobaccoPicId = storeTobaccoPicId;
    }

    public Boolean getStoreLicence() {
        return storeLicence;
    }

    public void setStoreLicence(Boolean storeLicence) {
        this.storeLicence = storeLicence;
    }

    public String getLicenceName() {
        return licenceName;
    }

    public void setLicenceName(String licenceName) {
        this.licenceName = licenceName;
    }

    public String getIndustryMcc() {
        return industryMcc;
    }

    public void setIndustryMcc(String industryMcc) {
        this.industryMcc = industryMcc;
    }

    public String getIndName() {
        return indName;
    }

    public void setIndName(String indName) {
        this.indName = indName;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getLicencePic() {
        return licencePic;
    }

    public void setLicencePic(String licencePic) {
        this.licencePic = licencePic;
    }

    public Long getLicencePicId() {
        return licencePicId;
    }

    public void setLicencePicId(Long licencePicId) {
        this.licencePicId = licencePicId;
    }
}
