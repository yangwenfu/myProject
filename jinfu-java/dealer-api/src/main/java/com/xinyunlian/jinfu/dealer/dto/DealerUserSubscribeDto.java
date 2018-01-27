package com.xinyunlian.jinfu.dealer.dto;

import com.xinyunlian.jinfu.dealer.enums.EDealerUserSubscribeType;

import java.io.Serializable;

/**
 * Created by menglei on 2017年08月03日.
 */
public class DealerUserSubscribeDto implements Serializable {

    private Long id;

    private String openId;

    private EDealerUserSubscribeType wechatType;

    private String dealerId;

    private String userId;

    private String createTime;

    private String userName;

    private String mobile;

    private String dealerName;

    private String belongName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public EDealerUserSubscribeType getWechatType() {
        return wechatType;
    }

    public void setWechatType(EDealerUserSubscribeType wechatType) {
        this.wechatType = wechatType;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }
}
