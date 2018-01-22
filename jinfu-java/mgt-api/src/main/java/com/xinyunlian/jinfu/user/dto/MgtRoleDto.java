package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DongFC on 2016-08-19.
 */
public class MgtRoleDto implements Serializable {

    private static final long serialVersionUID = -3901192197396287568L;

    private Long roleId;

    private String roleCode;

    private String roleName;

    private String roleDesc;

    private String createOpId;

    private Date createTs;

    private String lastMntOpId;

    private Date lastMntTs;

    private Long versionCt;

    private MgtPermissionDto permRoot;

    private List<MgtPermissionDto> mgtPermissionDtoList = new ArrayList<>();

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

    public List<MgtPermissionDto> getMgtPermissionDtoList() {
        return mgtPermissionDtoList;
    }

    public void setMgtPermissionDtoList(List<MgtPermissionDto> mgtPermissionDtoList) {
        this.mgtPermissionDtoList = mgtPermissionDtoList;
    }

    public String getCreateOpId() {
        return createOpId;
    }

    public void setCreateOpId(String createOpId) {
        this.createOpId = createOpId;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getLastMntOpId() {
        return lastMntOpId;
    }

    public void setLastMntOpId(String lastMntOpId) {
        this.lastMntOpId = lastMntOpId;
    }

    public Date getLastMntTs() {
        return lastMntTs;
    }

    public void setLastMntTs(Date lastMntTs) {
        this.lastMntTs = lastMntTs;
    }

    public Long getVersionCt() {
        return versionCt;
    }

    public void setVersionCt(Long versionCt) {
        this.versionCt = versionCt;
    }

    public MgtPermissionDto getPermRoot() {
        return permRoot;
    }

    public void setPermRoot(MgtPermissionDto permRoot) {
        this.permRoot = permRoot;
    }
}
