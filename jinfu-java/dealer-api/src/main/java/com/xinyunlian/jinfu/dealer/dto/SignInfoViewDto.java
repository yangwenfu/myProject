package com.xinyunlian.jinfu.dealer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2017年05月03日.
 */
public class SignInfoViewDto implements Serializable {

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

    private String storeName;

    private String storeProvince;

    private String storeCity;

    private String storeArea;

    private String storeStreet;

    private String storeAddress;

    private String storeUserName;

    private String storeUserMobile;

    private String userName;

    private String mobile;

    private String dealerName;

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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStoreProvince() {
        return storeProvince;
    }

    public void setStoreProvince(String storeProvince) {
        this.storeProvince = storeProvince;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public String getStoreArea() {
        return storeArea;
    }

    public void setStoreArea(String storeArea) {
        this.storeArea = storeArea;
    }

    public String getStoreStreet() {
        return storeStreet;
    }

    public void setStoreStreet(String storeStreet) {
        this.storeStreet = storeStreet;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreUserName() {
        return storeUserName;
    }

    public void setStoreUserName(String storeUserName) {
        this.storeUserName = storeUserName;
    }

    public String getStoreUserMobile() {
        return storeUserMobile;
    }

    public void setStoreUserMobile(String storeUserMobile) {
        this.storeUserMobile = storeUserMobile;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
}
