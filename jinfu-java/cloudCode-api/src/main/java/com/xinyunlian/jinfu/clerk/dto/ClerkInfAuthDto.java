package com.xinyunlian.jinfu.clerk.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016-12-06.
 */
public class ClerkInfAuthDto implements Serializable {

    private static final long serialVersionUID = -8720366343952539719L;

    private String clerkId;

    private String name;

    private String mobile;

    private String storeNames;

    private String createTime;

    private String openId;

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStoreNames() {
        return storeNames;
    }

    public void setStoreNames(String storeNames) {
        this.storeNames = storeNames;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
