package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;

/**
 * Created by King on 2017/3/14.
 */
public class DealerUserQRInfo implements Serializable {
    private static final long serialVersionUID = 2946831690022700338L;
    private String userId;
    private String qrType;
    private String qrCode;
    private String qrKey;
    private String qrUrl;
    private String name;
    private String mobile;
    private String livePic;
    private String createTime;
    private boolean qrStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQrType() {
        return qrType;
    }

    public void setQrType(String qrType) {
        this.qrType = qrType;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrKey() {
        return qrKey;
    }

    public void setQrKey(String qrKey) {
        this.qrKey = qrKey;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    public String getLivePic() {
        return livePic;
    }

    public void setLivePic(String livePic) {
        this.livePic = livePic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isQrStatus() {
        return qrStatus;
    }

    public void setQrStatus(boolean qrStatus) {
        this.qrStatus = qrStatus;
    }
}
