package com.xinyunlian.jinfu.api.dto;

import java.io.Serializable;

/**
 * Created by DongFC on 2016-11-03.
 */
public class UserBriefOpenApiDto implements Serializable {
    private static final long serialVersionUID = -6246016746064129707L;

    private String userId;
    private String mobile;
    private String userName;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
