package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.user.enums.EMgtUserStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by DongFC on 2016-08-19.
 */
public class MgtUserDto implements Serializable {

    private static final long serialVersionUID = 3661753903110867651L;

    private String userId;

    private String loginId;

    private String password;

    private String name;

    private EMgtUserStatus status;

    private String mobile;

    private String email;

    private String duty;

    private String createOpId;

    private Date createTs;

    private String lastMntOpId;

    private Date lastMntTs;

    private Long versionCt;

    private MgtDeptDto mgtDeptDto;

    private List<MgtRoleDto> mgtRoleDtoList;

    //用户未配置的角色
    private List<MgtRoleDto> roleNotHaveList;

    //用户已有的角色
    private List<MgtRoleDto> roleOwnedList;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EMgtUserStatus getStatus() {
        return status;
    }

    public void setStatus(EMgtUserStatus status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<MgtRoleDto> getMgtRoleDtoList() {
        return mgtRoleDtoList;
    }

    public void setMgtRoleDtoList(List<MgtRoleDto> mgtRoleDtoList) {
        this.mgtRoleDtoList = mgtRoleDtoList;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
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

    public List<MgtRoleDto> getRoleNotHaveList() {
        return roleNotHaveList;
    }

    public void setRoleNotHaveList(List<MgtRoleDto> roleNotHaveList) {
        this.roleNotHaveList = roleNotHaveList;
    }

    public List<MgtRoleDto> getRoleOwnedList() {
        return roleOwnedList;
    }

    public void setRoleOwnedList(List<MgtRoleDto> roleOwnedList) {
        this.roleOwnedList = roleOwnedList;
    }

    public MgtDeptDto getMgtDeptDto() {
        return mgtDeptDto;
    }

    public void setMgtDeptDto(MgtDeptDto mgtDeptDto) {
        this.mgtDeptDto = mgtDeptDto;
    }
}
