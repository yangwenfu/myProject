package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.qrcode.enums.EProdCodeStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2017/03/22.
 */

public class ApiProdCodeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String prodName;//商品名

    private String storeName;//购买地点

    private String supplier;//商品来源

    private String qrCodeNo;

    private String qrCodeUrl;

    private Date sellTime;//购买时间

    private Boolean frozen;//是否冻结

    private EProdCodeStatus status;//商品状态

    private String detailText;//商品详情

    private String lat;

    private String lng;

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
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

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getSellTime() {
        return sellTime;
    }

    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }

    public String getDetailText() {
        return detailText;
    }

    public void setDetailText(String detailText) {
        this.detailText = detailText;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
