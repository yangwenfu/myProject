package com.xinyunlian.jinfu.stats.dto;

import com.xinyunlian.jinfu.report.dealer.enums.EDealerStatsOrderStatus;

import java.io.Serializable;

/**
 * Created by menglei on 2016年08月31日.
 */
public class OrderInfoDto implements Serializable {

    private Long id;
    //private Long storeId;
    private String storeName;
    //private String storeCreateTs;
    //private String storeUserId;
    private String storeUserName;
    //private String userCreateTs;
    //private String prodId;
    private String prodName;
    private String orderNo;
    private EDealerStatsOrderStatus status;
    private String statusStr;
    //private String dealerId;
    //private String userId;
    private String createTs;
    private Integer type;//1:显示姓名，2：显示店名，3：显示姓名和店名
    private String dealerUserName;//分销员姓名
    private String dealerUserMobile;//分销员手机号

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

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public EDealerStatsOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EDealerStatsOrderStatus status) {
        this.status = status;
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
}
