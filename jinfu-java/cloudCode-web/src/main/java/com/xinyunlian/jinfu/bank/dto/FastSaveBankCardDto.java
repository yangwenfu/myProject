package com.xinyunlian.jinfu.bank.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017/08/30.
 */

public class FastSaveBankCardDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;

    private Long storeId;

    private String userName;

    private String idCardNo;

    private String idCardFrontBase64;

    private String bankCardNo;

    private String bankMobile;

    private String verifyCode;

    private String referee;//推荐人

    private Long bankId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getIdCardFrontBase64() {
        return idCardFrontBase64;
    }

    public void setIdCardFrontBase64(String idCardFrontBase64) {
        this.idCardFrontBase64 = idCardFrontBase64;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }
}
