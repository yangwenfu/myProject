package com.xinyunlian.jinfu.api.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年03月22日.
 */
public class OrderProdOpenApiDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String prodName;//商品名

    private String sku;//sku

    private String boxCount;//箱数（带单位）

    private String prodCount;//商品数（带单位）

    private Long bindCount;//商品数量

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(String boxCount) {
        this.boxCount = boxCount;
    }

    public String getProdCount() {
        return prodCount;
    }

    public void setProdCount(String prodCount) {
        this.prodCount = prodCount;
    }

    public Long getBindCount() {
        return bindCount;
    }

    public void setBindCount(Long bindCount) {
        this.bindCount = bindCount;
    }
}
