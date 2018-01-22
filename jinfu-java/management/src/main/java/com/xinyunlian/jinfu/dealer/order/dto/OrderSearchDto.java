package com.xinyunlian.jinfu.dealer.order.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by menglei on 2016年10月08日.
 */
public class OrderSearchDto extends PagingDto<OrderDto> {

    private static final long serialVersionUID = 1L;

    private String orderNo;

    private String prodId;

    private String type;

    private String storeName;

    private String storeUserName;

    private String provinceId;

    private String cityId;

    private String areaId;

    private String streetId;

    private String dealerProvinceId;

    private String dealerCityId;

    private String dealerAreaId;

    private String dealerStreetId;

    private String beginTime;

    private String endTime;

    private String dealerName;

    private String userName;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreUserName() {
        return storeUserName;
    }

    public void setStoreUserName(String storeUserName) {
        this.storeUserName = storeUserName;
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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDealerProvinceId() {
        return dealerProvinceId;
    }

    public void setDealerProvinceId(String dealerProvinceId) {
        this.dealerProvinceId = dealerProvinceId;
    }

    public String getDealerCityId() {
        return dealerCityId;
    }

    public void setDealerCityId(String dealerCityId) {
        this.dealerCityId = dealerCityId;
    }

    public String getDealerAreaId() {
        return dealerAreaId;
    }

    public void setDealerAreaId(String dealerAreaId) {
        this.dealerAreaId = dealerAreaId;
    }

    public String getDealerStreetId() {
        return dealerStreetId;
    }

    public void setDealerStreetId(String dealerStreetId) {
        this.dealerStreetId = dealerStreetId;
    }
}
