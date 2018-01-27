package com.xinyunlian.jinfu.dealer.dto;

import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;
import com.xinyunlian.jinfu.user.dto.DealerUserDto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年09月02日.
 */
public class DealerUserLogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long logId;

    private String userId;

    private String dealerId;

    private String lng;

    private String lat;

    private String address;

    private EDealerUserLogType type;

    private String storeUserId;

    private String storeId;

    private String remark;

    private String createTime;

    private DealerUserDto dealerUserDto;

    private DealerDto dealerDto;

    private String storeName;

    private String storeUserName;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EDealerUserLogType getType() {
        return type;
    }

    public void setType(EDealerUserLogType type) {
        this.type = type;
    }

    public String getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(String storeUserId) {
        this.storeUserId = storeUserId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public DealerUserDto getDealerUserDto() {
        return dealerUserDto;
    }

    public void setDealerUserDto(DealerUserDto dealerUserDto) {
        this.dealerUserDto = dealerUserDto;
    }

    public DealerDto getDealerDto() {
        return dealerDto;
    }

    public void setDealerDto(DealerDto dealerDto) {
        this.dealerDto = dealerDto;
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
}
