package com.xinyunlian.jinfu.api.dto.resp;

import com.xinyunlian.jinfu.bank.enums.ECardType;

import java.io.Serializable;

/**
 * Created by KimLL on 2016/8/18.
 */

public class BankCardOpenApiResp implements Serializable {
    private static final long serialVersionUID = 1L;
    private long bankCardId;
    private String userId;
    //银行卡号
    private String bankCardNo;
    //发卡行名称
    private String bankName;
    //预留手机号
    private String mobileNo;
    //银行简写
    private String bankCode;
    //支行名称
    private String subbranchName;
    //支行号
    private String subbranchNo;
    //1-借记卡 2-信用卡
    private ECardType cardType;

    private String bankLogo;

    public long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(long bankCardId) {
        this.bankCardId = bankCardId;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getSubbranchName() {
        return subbranchName;
    }

    public void setSubbranchName(String subbranchName) {
        this.subbranchName = subbranchName;
    }

    public String getSubbranchNo() {
        return subbranchNo;
    }

    public void setSubbranchNo(String subbranchNo) {
        this.subbranchNo = subbranchNo;
    }

    public ECardType getCardType() {
        return cardType;
    }

    public void setCardType(ECardType cardType) {
        this.cardType = cardType;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }
}
