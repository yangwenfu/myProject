package com.xinyunlian.jinfu.channel.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by DongFC on 2016-08-23.
 */
public class ChannelUserSearchDto extends PagingDto<ChannelUserDto> {
    private static final long serialVersionUID = -6104791434152952083L;

    private String loginId;

    private String name;

    private String mobile;

    private String duty;

    private String parentUserId;

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

    public String getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(String parentUserId) {
        this.parentUserId = parentUserId;
    }
}
