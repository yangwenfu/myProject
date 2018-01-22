package com.xinyunlian.jinfu.user.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.user.enums.ETradeType;
import com.xinyunlian.jinfu.user.enums.converter.ETradeTypeConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行流水账户Entity
 *
 * @author jll
 */
@Entity
@Table(name = "USER_BANK_ACCT_TRD")
public class UserBankAcctTrdPo extends BaseMaintainablePo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BANK_ACCOUNT_ID")
    private Long bankAccountId;

    @Column(name = "TRADE_TYPE")
    @Convert(converter = ETradeTypeConverter.class)
    private ETradeType tradeType;

    @Column(name = "TRADE_DATE")
    private Date tradeDate;

    @Column(name = "TRADE_AMOUNT")
    private BigDecimal tradeAmount;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @Column(name = "REMARK")
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


