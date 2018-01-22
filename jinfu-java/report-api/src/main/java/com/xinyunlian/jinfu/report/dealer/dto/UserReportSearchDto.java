package com.xinyunlian.jinfu.report.dealer.dto;

import com.xinyunlian.jinfu.report.dealer.enums.EUserSource;
import com.xinyunlian.jinfu.report.dealer.enums.EUserStatus;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/30.
 */
public class UserReportSearchDto implements Serializable {
    private String mobile;

    private String userName;

    private Boolean identityAuth;

    private Boolean storeAuth;

    private String provinceId;

    private String cityId;

    private String areaId;

    private String streetId;

    private String registerStartDate;

    private String registerEndDate;

    private EUserSource userSource;

    private String tobaccoCertificateNo;

    private EUserStatus userStatus;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(Boolean identityAuth) {
        this.identityAuth = identityAuth;
    }

    public Boolean getStoreAuth() {
        return storeAuth;
    }

    public void setStoreAuth(Boolean storeAuth) {
        this.storeAuth = storeAuth;
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

    public String getRegisterStartDate() {
        return registerStartDate;
    }

    public void setRegisterStartDate(String registerStartDate) {
        this.registerStartDate = registerStartDate;
    }

    public String getRegisterEndDate() {
        return registerEndDate;
    }

    public void setRegisterEndDate(String registerEndDate) {
        this.registerEndDate = registerEndDate;
    }

    public EUserSource getUserSource() {
        return userSource;
    }

    public void setUserSource(EUserSource userSource) {
        this.userSource = userSource;
    }

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }

    public EUserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(EUserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
