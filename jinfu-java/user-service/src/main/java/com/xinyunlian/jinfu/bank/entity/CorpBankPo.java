package com.xinyunlian.jinfu.bank.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by dongfangchao on 2017/5/18/0018.
 */
@Entity
@Table(name = "corp_bank")
public class CorpBankPo extends BaseMaintainablePo {

    private static final long serialVersionUID = -8939948489962095694L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "BANK_SHORT_NAME")
    private String bankShortName;

    @Column(name = "OPENING_BANK")
    private String openingBank;

    @Column(name = "BANK_BRANCH")
    private String bankBranch;

    @Column(name = "ACCT_NAME")
    private String acctName;

    @Column(name = "ACCOUNT")
    private String account;

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

    public String getOpeningBank() {
        return openingBank;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }
}
