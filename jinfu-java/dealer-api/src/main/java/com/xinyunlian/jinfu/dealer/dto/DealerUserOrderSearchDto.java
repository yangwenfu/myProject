package com.xinyunlian.jinfu.dealer.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by menglei on 2016年08月31日.
 */
public class DealerUserOrderSearchDto extends PagingDto<DealerUserOrderDto> {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String dealerId;

    private String storeId;

    private String prodId;

    private String beginTime;

    private String endTime;

    private String lastId;//最大id,下拉加载用

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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
}
