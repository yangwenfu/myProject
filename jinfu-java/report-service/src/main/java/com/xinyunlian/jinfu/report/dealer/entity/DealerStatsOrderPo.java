package com.xinyunlian.jinfu.report.dealer.entity;

import com.xinyunlian.jinfu.report.dealer.enums.EDealerStatsOrderStatus;
import com.xinyunlian.jinfu.report.dealer.enums.converter.EDealeStatsOrderStatusConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2016/12/07.
 */
@Entity
@Table(name = "dealer_stats_order")
public class DealerStatsOrderPo implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "STORE_NAME")
    private String storeName;

    @Column(name = "STORE_CREATE_TS")
    private Date storeCreateTs;

    @Column(name = "STORE_USER_ID")
    private String storeUserId;

    @Column(name = "STORE_USER_NAME")
    private String storeUserName;

    @Column(name = "USER_CREATE_TS")
    private Date userCreateTs;

    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "PROD_NAME")
    private String prodName;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "STATUS")
    @Convert(converter = EDealeStatsOrderStatusConverter.class)
    private EDealerStatsOrderStatus status;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "CREATE_TS")
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

    public Date getStoreCreateTs() {
        return storeCreateTs;
    }

    public void setStoreCreateTs(Date storeCreateTs) {
        this.storeCreateTs = storeCreateTs;
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

    public Date getUserCreateTs() {
        return userCreateTs;
    }

    public void setUserCreateTs(Date userCreateTs) {
        this.userCreateTs = userCreateTs;
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

    public EDealerStatsOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EDealerStatsOrderStatus status) {
        this.status = status;
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

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
