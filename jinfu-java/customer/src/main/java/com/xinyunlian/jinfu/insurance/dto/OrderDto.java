package com.xinyunlian.jinfu.insurance.dto;

import java.io.Serializable;

/**
 * Created by jll on 2016/10/9.
 */
public class OrderDto implements Serializable {
    private Long storeId;

    private String prodId;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
}
