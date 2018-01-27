package com.xinyunlian.jinfu.channel.dto;

import java.io.Serializable;

/**
 * 渠道用户Entity
 *
 * @author jll
 */

public class ChannelUserRelationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String parentUserId;
    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(String parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}


