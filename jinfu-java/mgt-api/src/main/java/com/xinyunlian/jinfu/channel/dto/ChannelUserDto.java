package com.xinyunlian.jinfu.channel.dto;

import com.xinyunlian.jinfu.user.enums.EMgtUserStatus;

import java.io.Serializable;

/**
 * Created by jll on 2017-05-5.
 */
public class ChannelUserDto implements Serializable {

    private static final long serialVersionUID = 3661753903110867651L;

    private String userId;

    private String loginId;

    private String name;

    private EMgtUserStatus status;

    private String mobile;

    private String duty;

    private Long childrenCount;

    private Long areaCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EMgtUserStatus getStatus() {
        return status;
    }

    public void setStatus(EMgtUserStatus status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public Long getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(Long childrenCount) {
        this.childrenCount = childrenCount;
    }

    public Long getAreaCount() {
        return areaCount;
    }

    public void setAreaCount(Long areaCount) {
        this.areaCount = areaCount;
    }
}
