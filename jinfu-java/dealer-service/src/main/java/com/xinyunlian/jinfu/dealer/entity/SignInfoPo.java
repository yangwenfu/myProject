package com.xinyunlian.jinfu.dealer.entity;

import com.xinyunlian.jinfu.common.entity.BasePo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by menglei on 2017年05月03日.
 */
@Entity
@Table(name = "sign_info")
public class SignInfoPo extends BasePo {

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

    @Column(name = "SIGN_IN_STORE_INNER")
    private String signInStoreInner;

    @Column(name = "SIGN_IN_LNG")
    private String signInLng;

    @Column(name = "SIGN_IN_LAT")
    private String signInLat;

    @Column(name = "SIGN_IN_ADDRESS")
    private String signInAddress;

    @Column(name = "SIGN_OUT_TIME")
    private Date signOutTime;

    @Column(name = "SIGN_OUT_HEADER")
    private String signOutStoreHeader;

    @Column(name = "SIGN_OUT_LNG")
    private String signOutLng;

    @Column(name = "SIGN_OUT_LAT")
    private String signOutLat;

    @Column(name = "SIGN_OUT_ADDRESS")
    private String signOutAddress;

    @Column(name = "DISTANCE_TIME")
    private String distanceTime;

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

    public String getSignInStoreInner() {
        return signInStoreInner;
    }

    public void setSignInStoreInner(String signInStoreInner) {
        this.signInStoreInner = signInStoreInner;
    }

    public String getSignInLng() {
        return signInLng;
    }

    public void setSignInLng(String signInLng) {
        this.signInLng = signInLng;
    }

    public Date getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }

    public String getSignOutStoreHeader() {
        return signOutStoreHeader;
    }

    public void setSignOutStoreHeader(String signOutStoreHeader) {
        this.signOutStoreHeader = signOutStoreHeader;
    }

    public String getSignOutLng() {
        return signOutLng;
    }

    public void setSignOutLng(String signOutLng) {
        this.signOutLng = signOutLng;
    }

    public String getDistanceTime() {
        return distanceTime;
    }

    public void setDistanceTime(String distanceTime) {
        this.distanceTime = distanceTime;
    }

    public String getSignInLat() {
        return signInLat;
    }

    public void setSignInLat(String signInLat) {
        this.signInLat = signInLat;
    }

    public String getSignOutLat() {
        return signOutLat;
    }

    public void setSignOutLat(String signOutLat) {
        this.signOutLat = signOutLat;
    }

    public String getSignInAddress() {
        return signInAddress;
    }

    public void setSignInAddress(String signInAddress) {
        this.signInAddress = signInAddress;
    }

    public String getSignOutAddress() {
        return signOutAddress;
    }

    public void setSignOutAddress(String signOutAddress) {
        this.signOutAddress = signOutAddress;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }
}
