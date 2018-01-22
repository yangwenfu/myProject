package com.xinyunlian.jinfu.yunma.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年05月31日.
 */
public class YmThirdUserDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long id;
    private Long thirdConfigId;
    private String outId;
    private String mobile;
    private String userId;
    private Long memberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThirdConfigId() {
        return thirdConfigId;
    }

    public void setThirdConfigId(Long thirdConfigId) {
        this.thirdConfigId = thirdConfigId;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
