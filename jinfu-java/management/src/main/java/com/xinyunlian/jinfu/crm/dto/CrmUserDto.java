package com.xinyunlian.jinfu.crm.dto;

import com.xinyunlian.jinfu.crm.enums.ECrmUserStatus;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KimLL on 2016/8/18.
 */

public class CrmUserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;

    //手机
    private String mobile;
    //用户名字
    private String userName;
    //账号状态
    private ECrmUserStatus status;

    private List<StoreInfDto> storeInfDtos = new ArrayList<>();

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

    public ECrmUserStatus getStatus() {
        return status;
    }

    public void setStatus(ECrmUserStatus status) {
        this.status = status;
    }

    public List<StoreInfDto> getStoreInfDtos() {
        return storeInfDtos;
    }

    public void setStoreInfDtos(List<StoreInfDto> storeInfDtos) {
        this.storeInfDtos = storeInfDtos;
    }
}
