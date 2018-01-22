package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
public class QunarApplyContInsInfoReq implements Serializable {
    private static final long serialVersionUID = 1102242027437978575L;

    @JsonProperty("personnelType")
    private String personnelType;

    @JsonProperty("name")
    private String name;

    @JsonProperty("certificateType")
    private String certificateType;

    @JsonProperty("certificateNo")
    private String certificateNo;

    @JsonProperty("linkManName")
    private String linkManName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("mobileTelephone")
    private String mobileTelephone;

    @JsonProperty("address")
    private String address;

    public String getPersonnelType() {
        return personnelType;
    }

    public void setPersonnelType(String personnelType) {
        this.personnelType = personnelType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getLinkManName() {
        return linkManName;
    }

    public void setLinkManName(String linkManName) {
        this.linkManName = linkManName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileTelephone() {
        return mobileTelephone;
    }

    public void setMobileTelephone(String mobileTelephone) {
        this.mobileTelephone = mobileTelephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
