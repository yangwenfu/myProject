package com.xinyunlian.jinfu.prod.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年09月21日.
 */
public class OrderDto implements Serializable {

    private String storeId;

    private String prodId;

    private String logLng;

    private String logLat;

    private String logAddress;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getLogLng() {
        return logLng;
    }

    public void setLogLng(String logLng) {
        this.logLng = logLng;
    }

    public String getLogLat() {
        return logLat;
    }

    public void setLogLat(String logLat) {
        this.logLat = logLat;
    }

    public String getLogAddress() {
        return logAddress;
    }

    public void setLogAddress(String logAddress) {
        this.logAddress = logAddress;
    }
}
