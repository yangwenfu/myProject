package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;

/**
 * Created by DongFC on 2016-08-31.
 */
public class MgtUserPwdDto implements Serializable {
    private static final long serialVersionUID = -7335117123071481708L;

    private String userId;
    private String oldPassword;
    private String newPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
