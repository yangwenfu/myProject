package com.xinyunlian.jinfu.report.dealer.dto;

import com.xinyunlian.jinfu.report.dealer.enums.EDealerStatsOrderStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2016年08月31日.
 */
public class DealerStatsOrderDto implements Serializable {

    private Long id;
    private Long storeId;
    private String storeName;
    private Date storeCreateTs;
    private String storeUserId;
    private String storeUserName;
    private Date userCreateTs;
    private String prodId;
    private String prodName;
    private String orderNo;
    private EDealerStatsOrderStatus status;
    private String dealerId;
    private String userId;
    private Date createTs;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(String storeUserId) {
        this.storeUserId = storeUserId;
    }

    public String getStoreUserName() {
        return storeUserName;
    }

    public void setStoreUserName(String storeUserName) {
        this.storeUserName = storeUserName;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStoreCreateTs() {
        return storeCreateTs;
    }

    public void setStoreCreateTs(Date storeCreateTs) {
        this.storeCreateTs = storeCreateTs;
    }

    public Date getUserCreateTs() {
        return userCreateTs;
    }

    public void setUserCreateTs(Date userCreateTs) {
        this.userCreateTs = userCreateTs;
    }

    public EDealerStatsOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EDealerStatsOrderStatus status) {
        this.status = status;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }
}
