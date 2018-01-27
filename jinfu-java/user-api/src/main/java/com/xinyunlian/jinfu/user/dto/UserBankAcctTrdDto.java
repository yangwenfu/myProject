package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.user.enums.ETradeType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行流水账户Entity
 *
 * @author jll
 */

public class UserBankAcctTrdDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long bankAccountId;
    //收/支类型
    private ETradeType tradeType;
    //交易日期
    private Date tradeDate;
    //金额
    private BigDecimal tradeAmount;
    //账户余额
    private BigDecimal balance;
    //摘要
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public ETradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(ETradeType tradeType) {
        this.tradeType = tradeType;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}


