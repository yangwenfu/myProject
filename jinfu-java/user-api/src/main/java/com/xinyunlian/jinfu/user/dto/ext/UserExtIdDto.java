package com.xinyunlian.jinfu.user.dto.ext;

import java.io.Serializable;

/**
 * Created by King on 2017/2/17.
 */
public class UserExtIdDto implements Serializable{
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
