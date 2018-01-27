package com.xinyunlian.jinfu.user.entity;

import com.xinyunlian.jinfu.bank.entity.BankCardPo;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.store.entity.StoreInfPo;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.enums.EUserStatus;
import com.xinyunlian.jinfu.user.enums.converter.ESourceConverter;
import com.xinyunlian.jinfu.user.enums.converter.EUserStatusConverter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * Created by KimLL on 2016/8/18.
 */
@Entity
@Table(name = "USER_INF")
@EntityListeners(IdInjectionEntityListener.class)
public class UserInfoPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "LOGIN_PWD")
    private String loginPwd;

    @Column(name = "DEAL_PWD")
    private String dealPwd;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    @Column(name = "STATUS")
    @Convert(converter = EUserStatusConverter.class)
    private EUserStatus status;

    @Column(name = "IDENTITY_AUTH")
    private Boolean identityAuth;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "IDENTITY_AUTH_DATE")
    private Date identityAuthDate;

    @Column(name = "STORE_AUTH")
    private Boolean storeAuth;

    //安全卡认证，默认0，1为已认证
    @Column(name = "BANK_AUTH")
    private Boolean bankAuth;

    // 2017-02-06 收银台是否被冻结
    @Column(name = "CASHIER_FROZEN")
    private Boolean cashierFrozen;

    @Column(name = "SOURCE")
    @Convert(converter = ESourceConverter.class)
    private ESource source;

    @Column(name = "SOURCE_NAME")
    private String sourceName;

    @Column(name = "UUID")
    private String uuid;

    @Column(name = "AB_TEST")
    private String abTest;

    @Column(name = "DEALER_USER_ID")
    private String dealerUserId;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userInfoPo")
    private UserExtPo userExtPo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfoPo")
    private List<StoreInfPo> storeInfPoList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfoPo")
    private List<BankCardPo> bankCardPoList;

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

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getDealPwd() {
        return dealPwd;
    }

    public void setDealPwd(String dealPwd) {
        this.dealPwd = dealPwd;
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

    public List<StoreInfPo> getStoreInfPoList() {
        return storeInfPoList;
    }

    public void setStoreInfPoList(List<StoreInfPo> storeInfPoList) {
        this.storeInfPoList = storeInfPoList;
    }

    public List<BankCardPo> getBankCardPoList() {
        return bankCardPoList;
    }

    public void setBankCardPoList(List<BankCardPo> bankCardPoList) {
        this.bankCardPoList = bankCardPoList;
    }

    public UserExtPo getUserExtPo() {
        return userExtPo;
    }

    public void setUserExtPo(UserExtPo userExtPo) {
        this.userExtPo = userExtPo;
    }

    public ESource getSource() {
        return source;
    }

    public void setSource(ESource source) {
        this.source = source;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getIdentityAuthDate() {
        return identityAuthDate;
    }

    public void setIdentityAuthDate(Date identityAuthDate) {
        this.identityAuthDate = identityAuthDate;
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
}
