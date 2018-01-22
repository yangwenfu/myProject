package com.xinyunlian.jinfu.finance.dto;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;

/**
 * Created by dongfangchao on 2016/11/25/0025.
 */
public class BankCardExtDto extends BankCardDto {
    private static final long serialVersionUID = 6438312265876316446L;

    private Boolean zrfundsSupport = false;

    private String bankShortName;

    public Boolean getZrfundsSupport() {
        return zrfundsSupport;
    }

    public void setZrfundsSupport(Boolean zrfundsSupport) {
        this.zrfundsSupport = zrfundsSupport;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }
}
