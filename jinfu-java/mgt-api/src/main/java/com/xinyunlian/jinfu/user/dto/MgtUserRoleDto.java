package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;

/**
 * Created by DongFC on 2016-08-19.
 */
public class MgtUserRoleDto implements Serializable {

    private static final long serialVersionUID = -4420751797538594598L;

    private Long id;

    private String userId;

    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
