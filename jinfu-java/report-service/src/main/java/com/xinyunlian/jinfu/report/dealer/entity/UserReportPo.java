package com.xinyunlian.jinfu.report.dealer.entity;

import com.xinyunlian.jinfu.report.dealer.enums.EUserSource;
import com.xinyunlian.jinfu.report.dealer.enums.EUserStatus;
import com.xinyunlian.jinfu.report.dealer.enums.converter.EUserSourceEnumConverter;
import com.xinyunlian.jinfu.report.dealer.enums.converter.EUserStatusConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by bright on 2016/11/30.
 */
@Entity
@Table(name = "user")
public class UserReportPo {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "id_card_no")
    private String idCardNo;

    @Column(name = "identity_auth")
    private Boolean identityAuth;

    @Column(name = "store_auth")
    private Boolean storeAuth;

    @Column(name = "create_ts")
    private Date createTs;

    @Column(name = "identity_auth_date")
    private Date identityAuthDate;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "tobacco_certificate_no")
    private String tobaccoCertificateNo;

    @Column(name = "biz_licence")
    private String bizLicence;

    @Column(name = "province_id")
    private String provinceId;

    @Column(name = "province")
    private String province;

    @Column(name = "city_id")
    private String cityId;

    @Column(name = "city")
    private String city;

    @Column(name = "area_id")
    private String areaId;

    @Column(name = "area")
    private String area;

    @Column(name = "street_id")
    private String streetId;

    @Column(name = "street")
    private String street;

    @Column(name = "full_address")
    private String fullAddress;

    @Column(name = "user_source")
    @Convert(converter = EUserSourceEnumConverter.class)
    private EUserSource userSource;

    @Column(name = "dealer_user_name")
    private String dealerUserName;

    @Column(name = "dealer_user_mobile")
    private String dealerUserMobile;

    @Column(name = "dealer_name")
    private String dealerName;

    @Column(name = "user_status")
    @Convert( converter = EUserStatusConverter.class)
    private EUserStatus userStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
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

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Date getIdentityAuthDate() {
        return identityAuthDate;
    }

    public void setIdentityAuthDate(Date identityAuthDate) {
        this.identityAuthDate = identityAuthDate;
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

    public String getBizLicence() {
        return bizLicence;
    }

    public void setBizLicence(String bizLicence) {
        this.bizLicence = bizLicence;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public EUserSource getUserSource() {
        return userSource;
    }

    public void setUserSource(EUserSource userSource) {
        this.userSource = userSource;
    }

    public String getDealerUserName() {
        return dealerUserName;
    }

    public void setDealerUserName(String dealerUserName) {
        this.dealerUserName = dealerUserName;
    }

    public String getDealerUserMobile() {
        return dealerUserMobile;
    }

    public void setDealerUserMobile(String dealerUserMobile) {
        this.dealerUserMobile = dealerUserMobile;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
}
