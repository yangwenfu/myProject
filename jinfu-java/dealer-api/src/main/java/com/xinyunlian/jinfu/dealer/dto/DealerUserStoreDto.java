package com.xinyunlian.jinfu.dealer.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年08月31日.
 */
public class DealerUserStoreDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String dealerId;

    private String storeId;

    private String userId;

    private String beginTime;

    private String endTime;

    private DealerDto dealerDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public DealerDto getDealerDto() {
        return dealerDto;
    }

    public void setDealerDto(DealerDto dealerDto) {
        this.dealerDto = dealerDto;
    }
}
