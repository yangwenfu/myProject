package com.xinyunlian.jinfu.user.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dongfangchao on 2016/12/7/0007.
 */
public class MgtUserEditDto implements Serializable {
    private static final long serialVersionUID = -7975970352874846920L;

    @NotBlank(message = "userId can not be empty")
    private String userId;

    @NotBlank(message = "loginId can not be empty")
    private String loginId;

    //@NotBlank(message = "password can not be empty")
    private String password;

    @NotBlank(message = "name can not be empty")
    private String name;

    @NotBlank(message = "mobile can not be empty")
    private String mobile;

    private String email;

    private String duty;

    private List<MgtRoleDto> roleOwnedList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<MgtRoleDto> getRoleOwnedList() {
        return roleOwnedList;
    }

    public void setRoleOwnedList(List<MgtRoleDto> roleOwnedList) {
        this.roleOwnedList = roleOwnedList;
    }
}
