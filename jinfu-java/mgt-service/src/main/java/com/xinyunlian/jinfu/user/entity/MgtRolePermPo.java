package com.xinyunlian.jinfu.user.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by DongFC on 2016-08-19.
 */
@Entity
@Table(name = "mgt_role_perm")
public class MgtRolePermPo implements Serializable {

    private static final long serialVersionUID = 5131112448803539729L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "PERMISSION_ID")
    private Long permissionId;

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

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
