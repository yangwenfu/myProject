package com.xinyunlian.jinfu.user.dto;

import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DongFC on 2016-10-12.
 */
public class MgtPermissionDto implements Serializable {

    private static final long serialVersionUID = 6869131096230263403L;

    private Long permissionId;

    private String permissionCode;

    private String permissionName;

    private String permissionDesc;

    private Long permParent;

    private String permTreePath;

    private Long permOrder;

    private List<MgtPermissionDto> childPerms = new ArrayList<>();

    public void getChildPerms(List<MgtPermissionDto> allPerms){
        if (!CollectionUtils.isEmpty(allPerms)){
            allPerms.forEach( perm -> {
                if (this.getPermissionId() == perm.getPermParent()){
                    perm.getChildPerms(allPerms);
                    this.childPerms.add(perm);
                }
            });
        }
    }

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

    public List<MgtPermissionDto> getChildPerms() {
        return childPerms;
    }

    public void setChildPerms(List<MgtPermissionDto> childPerms) {
        this.childPerms = childPerms;
    }

    public Long getPermOrder() {
        return permOrder;
    }

    public void setPermOrder(Long permOrder) {
        this.permOrder = permOrder;
    }
}
