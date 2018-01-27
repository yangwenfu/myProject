package com.xinyunlian.jinfu.customer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinyunlian.jinfu.yunma.enums.EMemberStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by King on 2017/2/16.
 */
public class CustomerStoreDto implements Serializable{
    private static final long serialVersionUID = -1388275136850052497L;

    private Long storeId;

    private String storeName;

    private String province;

    private String city;

    private String area;

    private String street;

    private String address;

    private EMemberStatus qrStatus;

    private Long qrId;

    private String qrCodeNo;

    private Date qrBindTime;

    private Date createTs;

    private String tobaccoCertificateNo;

    private String bizLicence;

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

    public EMemberStatus getQrStatus() {
        return qrStatus;
    }

    public Long getQrId() {
        return qrId;
    }

    public void setQrId(Long qrId) {
        this.qrId = qrId;
    }

    public void setQrStatus(EMemberStatus qrStatus) {
        this.qrStatus = qrStatus;
    }

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
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

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    public Date getQrBindTime() {
        return qrBindTime;
    }

    public void setQrBindTime(Date qrBindTime) {
        this.qrBindTime = qrBindTime;
    }
}
