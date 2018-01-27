package com.xinyunlian.jinfu.order.dto;

import com.xinyunlian.jinfu.qrcode.dto.ProdCodeDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017年03月09日.
 */
public class ThirdOrderProdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long orderProdId;

    private String prodName;

    private Long orderId;

    private Long storeId;

    private Long prodId;

    private String sku;

    private String boxCount;

    private String prodCount;

    private Long bindCount;

    private List<ProdCodeDto> prodCodeList = new ArrayList<>();

    public Long getOrderProdId() {
        return orderProdId;
    }

    public void setOrderProdId(Long orderProdId) {
        this.orderProdId = orderProdId;
    }

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

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
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

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public List<ProdCodeDto> getProdCodeList() {
        return prodCodeList;
    }

    public void setProdCodeList(List<ProdCodeDto> prodCodeList) {
        this.prodCodeList = prodCodeList;
    }
}
