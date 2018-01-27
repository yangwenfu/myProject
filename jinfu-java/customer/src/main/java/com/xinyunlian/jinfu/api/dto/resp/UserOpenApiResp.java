package com.xinyunlian.jinfu.api.dto.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KimLL on 2016/8/18.
 */

public class UserOpenApiResp implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    //手机
    private String mobile;
    //用户名字
    private String userName;
    //身份证号
    private String idCardNo;
    //实名认证，默认0，1为已认证
    private Boolean identityAuth;

    List<StoreOpenApiResp> stores = new ArrayList<>();

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

    public Boolean getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(Boolean identityAuth) {
        this.identityAuth = identityAuth;
    }

    public List<StoreOpenApiResp> getStores() {
        return stores;
    }

    public void setStores(List<StoreOpenApiResp> stores) {
        this.stores = stores;
    }
}
