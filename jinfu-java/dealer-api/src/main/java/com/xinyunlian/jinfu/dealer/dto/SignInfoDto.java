package com.xinyunlian.jinfu.dealer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2017年05月03日.
 */
public class SignInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String dealerId;

    private String userId;

    private Long storeId;

    private Date signInTime;

    private String signInStoreHeader;

    private String signInStoreInner;

    private String signInLng;

    private String signInLat;

    private String signInAddress;

    private Date signOutTime;

    private String signOutStoreHeader;

    private String signOutLng;

    private String signOutLat;

    private String signOutAddress;

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
