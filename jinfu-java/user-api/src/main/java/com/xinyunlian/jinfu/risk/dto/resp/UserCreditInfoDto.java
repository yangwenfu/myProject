package com.xinyunlian.jinfu.risk.dto.resp;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/16.
 */
public class UserCreditInfoDto implements Serializable{

    private String userId;

    private String queryDate;

    /* 电话号码信息 */
    private String phoneInfo;

    /* 预期信息 */
    private String overDueInfo;

    /* 贷款信息 */
    private String loanInfo;

    /* 黑名单信息 */
    private String blacklistInfo;

    /* 其它机构查询情况 */
    private String logStatitics;

    /* 身份信息验证 */
    private String idCheck;

    /* 银行卡实名验证 */
    private String bankCardAuth;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public String getPhoneInfo() {
        return phoneInfo;
    }

    public void setPhoneInfo(String phoneInfo) {
        this.phoneInfo = phoneInfo;
    }

    public String getOverDueInfo() {
        return overDueInfo;
    }

    public void setOverDueInfo(String overDueInfo) {
        this.overDueInfo = overDueInfo;
    }

    public String getLoanInfo() {
        return loanInfo;
    }

    public void setLoanInfo(String loanInfo) {
        this.loanInfo = loanInfo;
    }

    public String getBlacklistInfo() {
        return blacklistInfo;
    }

    public void setBlacklistInfo(String blacklistInfo) {
        this.blacklistInfo = blacklistInfo;
    }

    public String getLogStatitics() {
        return logStatitics;
    }

    public void setLogStatitics(String logStatitics) {
        this.logStatitics = logStatitics;
    }

    public String getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(String idCheck) {
        this.idCheck = idCheck;
    }

    public String getBankCardAuth() {
        return bankCardAuth;
    }

    public void setBankCardAuth(String bankCardAuth) {
        this.bankCardAuth = bankCardAuth;
    }
}
