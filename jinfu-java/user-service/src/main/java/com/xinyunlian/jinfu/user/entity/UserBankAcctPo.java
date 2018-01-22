package com.xinyunlian.jinfu.user.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行流水账户Entity
 *
 * @author jll
 */
@Entity
@Table(name = "USER_BANK_ACCT")
public class UserBankAcctPo extends BaseMaintainablePo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BANK_ACCOUNT_ID")
    private Long bankAccountId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "BANK_CARD_NO")
    private String bankCardNo;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @Column(name = "TRADE_BEGIN")
    private Date tradeBegin;

    @Column(name = "TRADE_END")
    private Date tradeEnd;

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getTradeBegin() {
        return tradeBegin;
    }

    public void setTradeBegin(Date tradeBegin) {
        this.tradeBegin = tradeBegin;
    }

    public Date getTradeEnd() {
        return tradeEnd;
    }

    public void setTradeEnd(Date tradeEnd) {
        this.tradeEnd = tradeEnd;
    }
}


