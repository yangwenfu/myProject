package com.xinyunlian.jinfu.qrcode.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.qrcode.enums.EProdCodeStatus;
import com.xinyunlian.jinfu.qrcode.enums.converter.EProdCodeStatusConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品码库
 *
 * @author menglei
 */
@Entity
@Table(name = "prod_code")
public class ProdCodePo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROD_CODE_ID")
    private Long prodCodeId;

    @Column(name = "ORDER_PROD_ID")
    private Long orderProdId;

    @Column(name = "PROD_ID")
    private Long prodId;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "PROD_ORDER_NO")
    private String prodOrderNo;

    @Column(name = "QRCODE_NO")
    private String qrCodeNo;

    @Column(name = "QRCODE_URL")
    private String qrCodeUrl;

    @Column(name = "BIND_TIME")
    private Date bindTime;

    @Column(name = "SELL_TIME")
    private Date sellTime;

    @Column(name = "FROZEN")
    private Boolean frozen;

    @Column(name = "STATUS")
    @Convert(converter = EProdCodeStatusConverter.class)
    private EProdCodeStatus status;

    @Column(name = "BATCH_NO")
    private String batchNo;

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

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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

    public String getProdOrderNo() {
        return prodOrderNo;
    }

    public void setProdOrderNo(String prodOrderNo) {
        this.prodOrderNo = prodOrderNo;
    }

    public Date getSellTime() {
        return sellTime;
    }

    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}


