package com.xinyunlian.jinfu.user.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by DongFC on 2016-10-12.
 */
@Entity
@Table(name = "mgt_permission")
public class MgtPermissionPo extends BaseMaintainablePo {

    private static final long serialVersionUID = -8601114698002390046L;

    @Id
    @Column(name = "PERMISSION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    @Column(name = "PERMISSION_CODE")
    private String permissionCode;

    @Column(name = "PERMISSION_NAME")
    private String permissionName;

    @Column(name = "PERMISSION_DESC")
    private String permissionDesc;

    @Column(name = "PERM_PARENT")
    private Long permParent;

    @Column(name = "PERM_TREE_PATH")
    private String permTreePath;

    @Column(name = "PERM_ORDER")
    private Long permOrder;

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Long getPermParent() {
        return permParent;
    }

    public void setPermParent(Long permParent) {
        this.permParent = permParent;
    }

    public String getPermTreePath() {
        return permTreePath;
    }

    public void setPermTreePath(String permTreePath) {
        this.permTreePath = permTreePath;
    }

    public Long getPermOrder() {
        return permOrder;
    }

    public void setPermOrder(Long permOrder) {
        this.permOrder = permOrder;
    }
}
