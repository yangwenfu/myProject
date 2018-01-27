package com.xinyunlian.jinfu.finaccbankcard.dto;

import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOpenAccType;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public class FinAccBankCardSearchDto implements Serializable{
    private static final long serialVersionUID = -89804992460322169L;

    private Long Id;

    private String userId;

    private String extTxAccId;

    private String userRealName;

    private String idCardNo;

    private String bankCardNo;

    private String reserveMobile;

    private EFinOrg finOrg;

    private String bankShortName;

    private EFinOpenAccType finOpenAccType;

    private String verifyCode;

    //中融的银行代码
    private String bankCode;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExtTxAccId() {
        return extTxAccId;
    }

    public void setExtTxAccId(String extTxAccId) {
        this.extTxAccId = extTxAccId;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getReserveMobile() {
        return reserveMobile;
    }

    public void setReserveMobile(String reserveMobile) {
        this.reserveMobile = reserveMobile;
    }

    public EFinOrg getFinOrg() {
        return finOrg;
    }

    public void setFinOrg(EFinOrg finOrg) {
        this.finOrg = finOrg;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }

    public EFinOpenAccType getFinOpenAccType() {
        return finOpenAccType;
    }

    public void setFinOpenAccType(EFinOpenAccType finOpenAccType) {
        this.finOpenAccType = finOpenAccType;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
