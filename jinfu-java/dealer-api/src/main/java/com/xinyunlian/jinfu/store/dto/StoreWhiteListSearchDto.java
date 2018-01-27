package com.xinyunlian.jinfu.store.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.store.enums.EStoreWhiteListRemark;
import com.xinyunlian.jinfu.store.enums.EStoreWhiteListStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2016/9/1.
 */
public class StoreWhiteListSearchDto extends PagingDto<StoreWhiteListDto> {
    private static final long serialVersionUID = 1L;

    private String storeName;

    private String province;

    private String city;

    private String area;

    private String street;

    private String areaId;

    private String provinceId;

    private String cityId;

    private String streetId;

    private String address;

    private String phone;

    private String userName;

    private String dealerId;

    private String userId;

    private String dealerName;

    private String dealerUserName;

    private Boolean unassigned = false;//是否未指派 true=未指派

    private Boolean notRemark = false;//是否未提交拜访结果 true=未提交

    private EStoreWhiteListStatus status;

    private EStoreWhiteListRemark remark;

    private List<String> dealerIds = new ArrayList<>();

    private List<String> userIds = new ArrayList<>();

    private List<Long> ids = new ArrayList<>();

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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public List<String> getDealerIds() {
        return dealerIds;
    }

    public void setDealerIds(List<String> dealerIds) {
        this.dealerIds = dealerIds;
    }

    public Boolean getUnassigned() {
        return unassigned;
    }

    public void setUnassigned(Boolean unassigned) {
        this.unassigned = unassigned;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getNotRemark() {
        return notRemark;
    }

    public void setNotRemark(Boolean notRemark) {
        this.notRemark = notRemark;
    }

    public EStoreWhiteListRemark getRemark() {
        return remark;
    }

    public void setRemark(EStoreWhiteListRemark remark) {
        this.remark = remark;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getDealerUserName() {
        return dealerUserName;
    }

    public void setDealerUserName(String dealerUserName) {
        this.dealerUserName = dealerUserName;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
