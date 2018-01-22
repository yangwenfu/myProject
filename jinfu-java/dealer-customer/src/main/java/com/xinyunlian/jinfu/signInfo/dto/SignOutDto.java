package com.xinyunlian.jinfu.signInfo.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by menglei on 2017年05月03日.
 */
public class SignOutDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "店铺id不能为空")
    private Long storeId;

    @NotBlank(message = "店铺门头照不能为空")
    private String signOutStoreHeaderBase64;

    @NotBlank(message = "坐标不能为空")
    private String signOutLng;

    @NotBlank(message = "坐标不能为空")
    private String signOutLat;

    @NotBlank(message = "地址不能为空")
    private String signOutAddress;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getSignOutStoreHeaderBase64() {
        return signOutStoreHeaderBase64;
    }

    public void setSignOutStoreHeaderBase64(String signOutStoreHeaderBase64) {
        this.signOutStoreHeaderBase64 = signOutStoreHeaderBase64;
    }

    public String getSignOutLng() {
        return signOutLng;
    }

    public void setSignOutLng(String signOutLng) {
        this.signOutLng = signOutLng;
    }

    public String getSignOutLat() {
        return signOutLat;
    }

    public void setSignOutLat(String signOutLat) {
        this.signOutLat = signOutLat;
    }

    public String getSignOutAddress() {
        return signOutAddress;
    }

    public void setSignOutAddress(String signOutAddress) {
        this.signOutAddress = signOutAddress;
    }
}
