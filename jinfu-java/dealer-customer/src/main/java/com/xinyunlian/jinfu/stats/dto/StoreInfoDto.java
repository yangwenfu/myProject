package com.xinyunlian.jinfu.stats.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年08月31日.
 */
public class StoreInfoDto implements Serializable {

    private Long id;
    private String storeName;
    private String storeCreateTs;
    private String storeUserName;
    private String userCreateTs;
    private Boolean isQrCode;//true:存在，false:不存在
    private String dealerUserName;//分销员姓名
    private String dealerUserMobile;//分销员手机号

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreUserName() {
        return storeUserName;
    }

    public void setStoreUserName(String storeUserName) {
        this.storeUserName = storeUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreCreateTs() {
        return storeCreateTs;
    }

    public void setStoreCreateTs(String storeCreateTs) {
        this.storeCreateTs = storeCreateTs;
    }

    public String getUserCreateTs() {
        return userCreateTs;
    }

    public void setUserCreateTs(String userCreateTs) {
        this.userCreateTs = userCreateTs;
    }

    public Boolean getQrCode() {
        return isQrCode;
    }

    public void setQrCode(Boolean qrCode) {
        isQrCode = qrCode;
    }

    public String getDealerUserName() {
        return dealerUserName;
    }

    public void setDealerUserName(String dealerUserName) {
        this.dealerUserName = dealerUserName;
    }

    public String getDealerUserMobile() {
        return dealerUserMobile;
    }

    public void setDealerUserMobile(String dealerUserMobile) {
        this.dealerUserMobile = dealerUserMobile;
    }
}
