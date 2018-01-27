package com.xinyunlian.jinfu.clerk.dto;

import com.xinyunlian.jinfu.clerk.enums.EClerkApplyStatus;

import java.io.Serializable;

/**
 * Created by menglei on 2016-12-06.
 */
public class ClerkApplyDto implements Serializable {

    private static final long serialVersionUID = -8720366343952539719L;

    private Long applyId;

    private String userId;

    private String clerkId;

    private EClerkApplyStatus status;

    private String createTime;

    private String updateTime;

    private String name;

    private String mobile;

    private String openId;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public EClerkApplyStatus getStatus() {
        return status;
    }

    public void setStatus(EClerkApplyStatus status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
