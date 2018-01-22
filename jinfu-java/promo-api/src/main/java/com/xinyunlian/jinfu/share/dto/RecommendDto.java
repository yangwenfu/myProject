package com.xinyunlian.jinfu.share.dto;

import java.io.Serializable;

/**
 * 推荐Entity
 *
 * @author jll
 */

public class RecommendDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    //被推荐人用户id
    private String userId;
    //推荐人用户id
    private String refereeUserId;
    //是否办理业务
    private Boolean order = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRefereeUserId() {
        return refereeUserId;
    }

    public void setRefereeUserId(String refereeUserId) {
        this.refereeUserId = refereeUserId;
    }

    public Boolean getOrder() {
        return order;
    }

    public void setOrder(Boolean order) {
        this.order = order;
    }
}


