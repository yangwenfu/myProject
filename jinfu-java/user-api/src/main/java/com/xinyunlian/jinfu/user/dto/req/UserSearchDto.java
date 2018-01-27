package com.xinyunlian.jinfu.user.dto.req;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.enums.ESource;

/**
 * Created by KimLL on 2016/8/26.
 */
public class UserSearchDto extends PagingDto<UserInfoDto> {
    private String mobile;

    private String email;

    private String userName;

    private String storeName;

    private String idCardNo;

    private Boolean identityAuth;

    private Boolean storeAuth;

    private Boolean bankAuth;

    private String province;

    private String city;

    private String area;

    private String street;

    private String provinceId;

    private String cityId;

    private String areaId;

    private String streetId;

    private String idAuthStartDate;

    private String idAuthEndDate;

    private String registerStartDate;

    private String registerEndDate;

    private String lastMntStartDate;

    private ESource userSource;

    private ESource storeSource;

    private String tobaccoCertificateNo;

    // 2017-02-06 收银台是否被冻结
    private Boolean cashierFrozen;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
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

    public String getIdAuthStartDate() {
        return idAuthStartDate;
    }

    public void setIdAuthStartDate(String idAuthStartDate) {
        this.idAuthStartDate = idAuthStartDate;
    }

    public String getIdAuthEndDate() {
        return idAuthEndDate;
    }

    public void setIdAuthEndDate(String idAuthEndDate) {
        this.idAuthEndDate = idAuthEndDate;
    }

    public String getLastMntStartDate() {
        return lastMntStartDate;
    }

    public void setLastMntStartDate(String lastMntStartDate) {
        this.lastMntStartDate = lastMntStartDate;
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

    public Boolean getBankAuth() {
        return bankAuth;
    }

    public void setBankAuth(Boolean bankAuth) {
        this.bankAuth = bankAuth;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public ESource getUserSource() {
        return userSource;
    }

    public void setUserSource(ESource userSource) {
        this.userSource = userSource;
    }

    public ESource getStoreSource() {
        return storeSource;
    }

    public void setStoreSource(ESource storeSource) {
        this.storeSource = storeSource;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }

    public Boolean getCashierFrozen() {
        return cashierFrozen;
    }

    public void setCashierFrozen(Boolean cashierFrozen) {
        this.cashierFrozen = cashierFrozen;
    }
}
