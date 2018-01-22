package com.xinyunlian.jinfu.uplus.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2017年03月09日.
 */
public class ThirdOrderInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long orderId;

    private Long storeId;

    private String orderNo;

    private String supplier;

    private String platform;

    private Date orderTime;

    private String storageMode;

    private Date storageTime;

    private Integer bindCount = 0;//可绑数量

    private Integer bindingCount = 0;//已绑数量

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getStorageMode() {
        return storageMode;
    }

    public void setStorageMode(String storageMode) {
        this.storageMode = storageMode;
    }

    public Date getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Date storageTime) {
        this.storageTime = storageTime;
    }

    public Integer getBindCount() {
        return bindCount;
    }

    public void setBindCount(Integer bindCount) {
        this.bindCount = bindCount;
    }

    public Integer getBindingCount() {
        return bindingCount;
    }

    public void setBindingCount(Integer bindingCount) {
        this.bindingCount = bindingCount;
    }
}
