package com.xinyunlian.jinfu.dealer.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by menglei on 2016年08月31日.
 */
public class DealerStoreDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String dealerId;

    private String storeId;

    private String userId;

    private String provinceId;

    private String cityId;

    private String areaId;

    private String streetId;

    private String dealerName;

    private String storeIds;

    private List<String> storeIdsList;

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(String storeIds) {
        this.storeIds = storeIds;
    }

    public List<String> getStoreIdsList() {
        return storeIdsList;
    }

    public void setStoreIdsList(List<String> storeIdsList) {
        this.storeIdsList = storeIdsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
