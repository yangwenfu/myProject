package com.xinyunlian.jinfu.stats.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年06月08日.
 */
public class StatsMemberDto implements Serializable {

    private Long id;
    private String storeName;
    private String userName;
    private String bindTime;
    private String dealerUserName;//分销员姓名
    private String dealerUserMobile;//分销员手机号

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    public String getDealerUserName() {
        return dealerUserName;
    }

    public void setDealerUserName(String dealerUserName) {
        this.dealerUserName = dealerUserName;
    }

    public String getDealerUserMobile() {
        return dealerUserMobile;
    }

    public void setDealerUserMobile(String dealerUserMobile) {
        this.dealerUserMobile = dealerUserMobile;
    }
}
