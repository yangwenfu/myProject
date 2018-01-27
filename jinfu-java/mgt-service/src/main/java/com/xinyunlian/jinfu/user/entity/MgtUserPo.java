package com.xinyunlian.jinfu.user.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.user.enums.EMgtUserStatus;
import com.xinyunlian.jinfu.user.enums.converter.EMgtUserStatusConverter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by DongFC on 2016-08-18.
 */
@Entity
@Table(name = "mgt_user")
@EntityListeners(IdInjectionEntityListener.class)
public class MgtUserPo extends BaseMaintainablePo {

    private static final long serialVersionUID = -6852580881160434033L;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    @Convert(converter = EMgtUserStatusConverter.class)
    private EMgtUserStatus status;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DUTY")
    private String duty;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mgt_user_role", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<MgtRolePo> mgtRolePoList;

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

    public List<MgtRolePo> getMgtRolePoList() {
        return mgtRolePoList;
    }

    public void setMgtRolePoList(List<MgtRolePo> mgtRolePoList) {
        this.mgtRolePoList = mgtRolePoList;
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
}
