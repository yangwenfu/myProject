package com.xinyunlian.jinfu.store.dto.req;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by menglei on 2017年05月03日.
 */
public class StoreWhiteSignInDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "店铺id不能为空")
    private Long storeId;

    @NotBlank(message = "店铺门头照不能为空")
    private String signInStoreHeaderBase64;

    @NotBlank(message = "坐标不能为空")
    private String signInLng;

    @NotBlank(message = "坐标不能为空")
    private String signInLat;

    @NotBlank(message = "地址不能为空")
    private String signInAddress;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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

    public String getSignInStoreHeaderBase64() {
        return signInStoreHeaderBase64;
    }

    public void setSignInStoreHeaderBase64(String signInStoreHeaderBase64) {
        this.signInStoreHeaderBase64 = signInStoreHeaderBase64;
    }

}
