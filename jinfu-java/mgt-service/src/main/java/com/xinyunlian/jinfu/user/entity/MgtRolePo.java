package com.xinyunlian.jinfu.user.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.util.List;

/**
 * Created by DongFC on 2016-08-18.
 */
@Entity
@Table(name = "mgt_role")
public class MgtRolePo extends BaseMaintainablePo{
    private static final long serialVersionUID = -2959074102544580941L;

    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "ROLE_CODE")
    private String roleCode;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @Column(name = "ROLE_DESC")
    private String roleDesc;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mgt_role_perm", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"))
    private List<MgtPermissionPo> mgtPermissionPoList;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<MgtPermissionPo> getMgtPermissionPoList() {
        return mgtPermissionPoList;
    }

    public void setMgtPermissionPoList(List<MgtPermissionPo> mgtPermissionPoList) {
        this.mgtPermissionPoList = mgtPermissionPoList;
    }
}
