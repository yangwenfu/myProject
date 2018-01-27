package com.xinyunlian.jinfu.risk.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by bright on 2016/11/16.
 */
@Entity
@Table(name = "user_credit_info")
public class UserCreditInfoPo extends BaseMaintainablePo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "QUERY_DATE")
    private String queryDate;

    @Column(name = "PHONE_INFO")
    private String phoneInfo;

    @Column(name = "OVERDUE_INFO")
    private String overDueInfo;

    @Column(name = "LOAN_INFO")
    private String loanInfo;

    @Column(name = "BLACKLIST_INFO")
    private String blacklistInfo;

    @Column(name = "LOG_STATISTICS")
    private String logStatitics;

    @Column(name = "ID_CHECK")
    private String idCheck;

    @Column(name = "BANK_CARD_AUTH")
    private String bankCardAuth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
