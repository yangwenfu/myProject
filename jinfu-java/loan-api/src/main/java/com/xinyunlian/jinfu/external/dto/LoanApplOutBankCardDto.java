package com.xinyunlian.jinfu.external.dto;

import java.io.Serializable;

/**
 * @author willwang
 */
public class LoanApplOutBankCardDto implements Serializable{

    private String applId;

    private String bankCardNo;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }
}
