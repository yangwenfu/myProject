package com.xinyunlian.jinfu.qrcode.dto;

import com.xinyunlian.jinfu.qrcode.enums.EProdCodeStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2017年03月09日.
 */
public class ProdCodeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long prodCodeId;

    private Long orderProdId;

    private Long prodId;

    private Long storeId;

    private Long orderId;

    private String qrCodeNo;

    private String qrCodeUrl;

    private Date bindTime;

    private Date sellTime;

    private Boolean frozen;

    private EProdCodeStatus status;

    private String batchNo;

    private Date createTs;

    public Long getProdCodeId() {
        return prodCodeId;
    }

    public void setProdCodeId(Long prodCodeId) {
        this.prodCodeId = prodCodeId;
    }

    public Long getOrderProdId() {
        return orderProdId;
    }

    public void setOrderProdId(Long orderProdId) {
        this.orderProdId = orderProdId;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public EProdCodeStatus getStatus() {
        return status;
    }

    public void setStatus(EProdCodeStatus status) {
        this.status = status;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getSellTime() {
        return sellTime;
    }

    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}
