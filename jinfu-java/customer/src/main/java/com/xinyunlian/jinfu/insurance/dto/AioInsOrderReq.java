package com.xinyunlian.jinfu.insurance.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/3/2/0002.
 */
public class AioInsOrderReq implements Serializable {
    private static final long serialVersionUID = -6298462129245619749L;

    @NotBlank(message = "store_name必填")
    private String storeName;
    @NotBlank(message = "tobaccoCertificateNo必填")
    private String tobaccoCertificateNo;
    @NotBlank(message = "contactName必填")
    private String contactName;
    private String contactMobile;
    @NotBlank(message = "province必填")
    private String province;
    @NotBlank(message = "city必填")
    private String city;
    @NotBlank(message = "town必填")
    private String town;
    private String detailAddress;

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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
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

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
