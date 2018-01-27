package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年08月30日.
 */
public class DealerUserInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String mobile;

    private String name;

    private Boolean isAdmin;

    private String dealerName;

    private Boolean identityAuth;

    private Boolean Passed;

    private Boolean examPassed;

    private String token;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getExamPassed() {
        return examPassed;
    }

    public void setExamPassed(Boolean examPassed) {
        this.examPassed = examPassed;
    }

    public Boolean getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(Boolean identityAuth) {
        this.identityAuth = identityAuth;
    }

    public Boolean getPassed() {
        return Passed;
    }

    public void setPassed(Boolean passed) {
        Passed = passed;
    }
}
