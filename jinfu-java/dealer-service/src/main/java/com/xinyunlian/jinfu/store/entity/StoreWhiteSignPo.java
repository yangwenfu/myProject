package com.xinyunlian.jinfu.store.entity;

import com.xinyunlian.jinfu.common.entity.BasePo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by menglei on 2017年05月03日.
 */
@Entity
@Table(name = "store_white_sign")
public class StoreWhiteSignPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "SIGN_IN_TIME")
    private Date signInTime;

    @Column(name = "SIGN_IN_STORE_HEADER")
    private String signInStoreHeader;

    @Column(name = "SIGN_IN_LNG")
    private String signInLng;

    @Column(name = "SIGN_IN_LAT")
    private String signInLat;

    @Column(name = "SIGN_IN_ADDRESS")
    private String signInAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public String getSignInStoreHeader() {
        return signInStoreHeader;
    }

    public void setSignInStoreHeader(String signInStoreHeader) {
        this.signInStoreHeader = signInStoreHeader;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getSignInLng() {
        return signInLng;
    }

    public void setSignInLng(String signInLng) {
        this.signInLng = signInLng;
    }

    public String getSignInLat() {
        return signInLat;
    }

    public void setSignInLat(String signInLat) {
        this.signInLat = signInLat;
    }

    public String getSignInAddress() {
        return signInAddress;
    }

    public void setSignInAddress(String signInAddress) {
        this.signInAddress = signInAddress;
    }
}
