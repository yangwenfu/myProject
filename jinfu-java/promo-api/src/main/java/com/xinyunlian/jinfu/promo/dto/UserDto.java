package com.xinyunlian.jinfu.promo.dto;

import java.io.Serializable;

/**
 * Created by King on 2016/8/18.
 */

public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;

    //手机
    private String mobile;
    //邮件
    private String email;
    //用户名字
    private String userName;
    //身份证号
    private String idCardNo;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }
}
