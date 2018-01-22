package com.xinyunlian.jinfu.user.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by DongFC on 2016-08-19.
 */
@Entity
@Table(name = "mgt_user_role")
public class MgtUserRolePo implements Serializable {

    private static final long serialVersionUID = 7754619978697088070L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ROLE_ID")
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
