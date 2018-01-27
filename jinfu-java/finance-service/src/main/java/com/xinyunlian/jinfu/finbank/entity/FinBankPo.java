package com.xinyunlian.jinfu.finbank.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/11/24/0024.
 */
@Entity
@Table(name = "fin_bank")
public class FinBankPo implements Serializable {
    private static final long serialVersionUID = 5770487032002807229L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "BANK_CODE")
    private String bankCode;

    @Column(name = "SINGLE_ORDER_LIMIT")
    private String singleOrderLimit;

    @Column(name = "DAILY_LIMIT")
    private String dailyLimit;

    @Column(name = "BANK_SHORT_NAME")
    private String bankShortName;

    @Column(name = "BANK_LOGO")
    private String bankLogo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getSingleOrderLimit() {
        return singleOrderLimit;
    }

    public void setSingleOrderLimit(String singleOrderLimit) {
        this.singleOrderLimit = singleOrderLimit;
    }

    public String getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(String dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }
}
