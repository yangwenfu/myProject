package com.xinyunlian.jinfu.pingan.dto;

import java.io.Serializable;

/**
 * Created by DongFC on 2016-09-09.
 */
public class PolicyDto implements Serializable {
    private static final long serialVersionUID = 2276939093348065521L;

    private String storeName;
    private String tobaccoCertificateNo;
    private String assuranceOrderNo;
    private String contactName;
    private String contactMobile;
    private String provinceCnName;
    private String provinceCode;
    private String cityCnName;
    private String cityCode;
    private String countyCnName;
    private String countyCode;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAssuranceOrderNo() {
        return assuranceOrderNo;
    }

    public void setAssuranceOrderNo(String assuranceOrderNo) {
        this.assuranceOrderNo = assuranceOrderNo;
    }

    public String getCityCnName() {
        return cityCnName;
    }

    public void setCityCnName(String cityCnName) {
        this.cityCnName = cityCnName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCountyCnName() {
        return countyCnName;
    }

    public void setCountyCnName(String countyCnName) {
        this.countyCnName = countyCnName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getProvinceCnName() {
        return provinceCnName;
    }

    public void setProvinceCnName(String provinceCnName) {
        this.provinceCnName = provinceCnName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
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
}
