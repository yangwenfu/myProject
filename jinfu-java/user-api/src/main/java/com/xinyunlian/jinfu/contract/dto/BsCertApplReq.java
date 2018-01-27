package com.xinyunlian.jinfu.contract.dto;
import java.io.Serializable;
/**
 * Created by dongfangchao on 2017/2/8/0008.
 */
public class BsCertApplReq implements Serializable {
    private static final long serialVersionUID = 6030767190742780740L;
    private String userType;
    private String name;
    private String password;
    private String identityType;
    private String identity;
    private String province;
    private String city;
    private String address;
    private String duration;
    private String mobile;
    private String emial;
    private String orgCode;
    private String linkMan;
    private String linkIdCode;
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getIdentityType() {
        return identityType;
    }
    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }
    public String getIdentity() {
        return identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmial() {
        return emial;
    }
    public void setEmial(String emial) {
        this.emial = emial;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkIdCode() {
        return linkIdCode;
    }

    public void setLinkIdCode(String linkIdCode) {
        this.linkIdCode = linkIdCode;
    }
}