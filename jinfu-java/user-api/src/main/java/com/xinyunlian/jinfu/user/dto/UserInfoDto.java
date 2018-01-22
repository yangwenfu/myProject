package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.enums.EUserStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by KimLL on 2016/8/18.
 */

public class UserInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;

    //手机
    private String mobile;
    //邮件
    private String email;
    //用户名字
    private String userName;
    //身份证号
    private String idCardNo;
    //账号状态
    private EUserStatus status;

    private Date createTs;
    //登入密码
    private String loginPassword;
    //交易密码
    private String dealPassword;
    //实名认证，默认0，1为已认证
    private Boolean identityAuth;
    //店铺认证，默认0，1为已认证
    //安全卡认证，默认0，1为已认证
    private Boolean bankAuth = false;
    //实名时间
    private Date identityAuthDate;

    private Boolean storeAuth;
    //数据来源
    private ESource source;

    private String abTest;

    private String dealerUserId;

    // 2017-02-06 收银台是否被冻结
    private Boolean cashierFrozen = false;

    private String sourceName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public EUserStatus getStatus() {
        return status;
    }

    public void setStatus(EUserStatus status) {
        this.status = status;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getDealPassword() {
        return dealPassword;
    }

    public void setDealPassword(String dealPassword) {
        this.dealPassword = dealPassword;
    }

    public Boolean getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(Boolean identityAuth) {
        this.identityAuth = identityAuth;
    }

    public Boolean getStoreAuth() {
        return storeAuth;
    }

    public void setStoreAuth(Boolean storeAuth) {
        this.storeAuth = storeAuth;
    }

    public ESource getSource() {
        return source;
    }

    public void setSource(ESource source) {
        this.source = source;
    }

    public Date getIdentityAuthDate() {
        return identityAuthDate;
    }

    public void setIdentityAuthDate(Date identityAuthDate) {
        this.identityAuthDate = identityAuthDate;
    }

    public Boolean getBankAuth() {
        return bankAuth;
    }

    public void setBankAuth(Boolean bankAuth) {
        this.bankAuth = bankAuth;
    }

    public Boolean getCashierFrozen() {
        return cashierFrozen;
    }

    public void setCashierFrozen(Boolean cashierFrozen) {
        this.cashierFrozen = cashierFrozen;
    }

    public String getAbTest() {
        return abTest;
    }

    public void setAbTest(String abTest) {
        this.abTest = abTest;
    }

    public String getDealerUserId() {
        return dealerUserId;
    }

    public void setDealerUserId(String dealerUserId) {
        this.dealerUserId = dealerUserId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "userId='" + userId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
