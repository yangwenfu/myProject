package com.xinyunlian.jinfu.shopkeeper.dto.my;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by King on 2017/2/16.
 */
public class StoreEachDto implements Serializable{
    private static final long serialVersionUID = -1388275136850052497L;

    private Long storeId;

    private String storeName;

    private String province;

    private String city;

    private String area;

    private String street;

    private String address;

    private Boolean storeLicence;

    private String licenceName;

    private String mcc;

    private String licence;

    private boolean qrStatus;

    private String qrcodeUrl;

    private String indName;

    private Date createTs;

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

    public boolean isQrStatus() {
        return qrStatus;
    }

    public void setQrStatus(boolean qrStatus) {
        this.qrStatus = qrStatus;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
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

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getIndName() {
        return indName;
    }

    public void setIndName(String indName) {
        this.indName = indName;
    }
}
