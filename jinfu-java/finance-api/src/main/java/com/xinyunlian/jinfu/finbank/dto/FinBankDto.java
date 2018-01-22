package com.xinyunlian.jinfu.finbank.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/11/24/0024.
 */
public class FinBankDto implements Serializable {
    private static final long serialVersionUID = 5770487032002807229L;

    private Long id;

    private String bankName;

    private String bankCode;

    private String singleOrderLimit;

    private String dailyLimit;

    private String bankShortName;

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
