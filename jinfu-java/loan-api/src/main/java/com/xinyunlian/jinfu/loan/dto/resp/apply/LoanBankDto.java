package com.xinyunlian.jinfu.loan.dto.resp.apply;

import java.io.Serializable;

/**
 * Created by Willwang on 2017/1/10.
 */
public class LoanBankDto implements Serializable {

    private Long bankCardId;

    private String bankCode;

    private String bankName;

    private String lastNo;

    private String logo;

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLastNo() {
        return lastNo;
    }

    public void setLastNo(String lastNo) {
        this.lastNo = lastNo;
    }
}
