package com.xinyunlian.jinfu.store.dto;

import com.xinyunlian.jinfu.store.enums.EStoreWhiteListRemark;
import com.xinyunlian.jinfu.store.enums.EStoreWhiteListStatus;

import java.io.Serializable;

/**
 * Created by menglei on 2017年06月20日.
 */
public class StoreWhiteListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String storeName;

    private String provinceId;

    private String cityId;

    private String areaId;

    private String streetId;

    private String province;

    private String city;

    private String area;

    private String street;

    private String address;

    private String userName;

    private String phone;

    private String dealerId;

    private String userId;

    private String dealerName;

    private String dealerUserName;

    private EStoreWhiteListStatus status;

    private EStoreWhiteListRemark remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public EStoreWhiteListStatus getStatus() {
        return status;
    }

    public void setStatus(EStoreWhiteListStatus status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public EStoreWhiteListRemark getRemark() {
        return remark;
    }

    public void setRemark(EStoreWhiteListRemark remark) {
        this.remark = remark;
    }

    public String getDealerUserName() {
        return dealerUserName;
    }

    public void setDealerUserName(String dealerUserName) {
        this.dealerUserName = dealerUserName;
    }
}
