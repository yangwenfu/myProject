package com.xinyunlian.jinfu.store.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.store.enums.EStoreWhiteListRemark;
import com.xinyunlian.jinfu.store.enums.EStoreWhiteListStatus;
import com.xinyunlian.jinfu.store.enums.converter.EStoreWhiteListRemarkConverter;
import com.xinyunlian.jinfu.store.enums.converter.EStoreWhiteListStatusConverter;

import javax.persistence.*;

/**
 * Created by menglei on 2017年06月20日.
 */
@Entity
@Table(name = "store_white_list")
public class StoreWhiteListPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STORE_NAME")
    private String storeName;

    @Column(name = "PROVINCE_ID")
    private String provinceId;

    @Column(name = "CITY_ID")
    private String cityId;

    @Column(name = "AREA_ID")
    private String areaId;

    @Column(name = "STREET_ID")
    private String streetId;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "STREET")
    private String street;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "STATUS")
    @Convert(converter = EStoreWhiteListStatusConverter.class)
    private EStoreWhiteListStatus status;

    @Column(name = "REMARK")
    @Convert(converter = EStoreWhiteListRemarkConverter.class)
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
}
