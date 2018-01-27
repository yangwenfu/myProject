package com.xinyunlian.jinfu.push.dto;

import java.io.Serializable;

/**
 * Created by apple on 2017/1/12.
 */
public class PushDeviceDto implements Serializable {

    private String pushToken;

    private String userId;

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
