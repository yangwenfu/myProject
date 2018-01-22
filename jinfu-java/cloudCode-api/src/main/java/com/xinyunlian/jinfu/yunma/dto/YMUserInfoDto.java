package com.xinyunlian.jinfu.yunma.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年01月04日.
 */
public class YMUserInfoDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private String ymUserId;
    private String openId;
    private String userId;
    private String mobile;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getYmUserId() {
        return ymUserId;
    }

    public void setYmUserId(String ymUserId) {
        this.ymUserId = ymUserId;
    }
}
