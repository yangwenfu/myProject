package com.xinyunlian.jinfu.shopkeeper.dto.my;

import com.xinyunlian.jinfu.user.enums.EAuthInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by King on 2017/2/14.
 */
public class UserDto implements Serializable{
    private static final long serialVersionUID = -2834106670757382573L;
    private String userId;
    //手机
    private String mobile;
    //用户名字
    private String userName;
    //实名认证，默认0，1为已认证
    private Boolean identityAuth;
    //实名时间
    private Date identityAuthDate;

    private boolean idAuthIsFull;

    private List<EAuthInfo> authInfos = new ArrayList<>();

    private String authInfo;

    private long bankCardCount;

    private long cardCount;

    private String token;

    //是否已签到
    private Boolean signIn = false;

    //用户积分
    private Long userScore;

    //是否云码用户
    private Boolean ymMember = false;

    private String abTest;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(Boolean identityAuth) {
        this.identityAuth = identityAuth;
    }

    public Date getIdentityAuthDate() {
        return identityAuthDate;
    }

    public void setIdentityAuthDate(Date identityAuthDate) {
        this.identityAuthDate = identityAuthDate;
    }

    public boolean isIdAuthIsFull() {
        return idAuthIsFull;
    }

    public void setIdAuthIsFull(boolean idAuthIsFull) {
        this.idAuthIsFull = idAuthIsFull;
    }

    public List<EAuthInfo> getAuthInfos() {
        return authInfos;
    }

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }

    public void setAuthInfos(List<EAuthInfo> authInfos) {
        this.authInfos = authInfos;
    }

    public long getBankCardCount() {
        return bankCardCount;
    }

    public void setBankCardCount(long bankCardCount) {
        this.bankCardCount = bankCardCount;
    }

    public long getCardCount() {
        return cardCount;
    }

    public void setCardCount(long cardCount) {
        this.cardCount = cardCount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getSignIn() {
        return signIn;
    }

    public void setSignIn(Boolean signIn) {
        this.signIn = signIn;
    }

    public Long getUserScore() {
        return userScore;
    }

    public void setUserScore(Long userScore) {
        this.userScore = userScore;
    }

    public Boolean getYmMember() {
        return ymMember;
    }

    public void setYmMember(Boolean ymMember) {
        this.ymMember = ymMember;
    }

    public String getAbTest() {
        return abTest;
    }

    public void setAbTest(String abTest) {
        this.abTest = abTest;
    }
}
