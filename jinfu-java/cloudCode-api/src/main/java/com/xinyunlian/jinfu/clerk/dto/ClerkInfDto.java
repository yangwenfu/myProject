package com.xinyunlian.jinfu.clerk.dto;

import com.xinyunlian.jinfu.clerk.enums.EClerkApplyStatus;

import java.io.Serializable;

/**
 * Created by menglei on 2016-12-06.
 */
public class ClerkInfDto  implements Serializable {

    private static final long serialVersionUID = -8720366343952539719L;

    private String clerkId;

    private String name;

    private String mobile;

    private String openId;

    private Boolean isApply;//true能申请，false已申请

    private EClerkApplyStatus status;

    private String createTime;

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Boolean getApply() {
        return isApply;
    }

    public void setApply(Boolean apply) {
        isApply = apply;
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
}
