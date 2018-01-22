package com.xinyunlian.jinfu.finbank.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dongfangchao on 2016/11/25/0025.
 */
public class FinBankSearchDto implements Serializable {
    private static final long serialVersionUID = 4283110006818110603L;

    private Long id;

    private String bankName;

    private String bankCode;

    private String singleOrderLimit;

    private String dailyLimit;

    private String bankShortName;

    private String bankLogo;

    private List<String> bankShortNames;

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

    public List<String> getBankShortNames() {
        return bankShortNames;
    }

    public void setBankShortNames(List<String> bankShortNames) {
        this.bankShortNames = bankShortNames;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }
}
