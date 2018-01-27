package com.xinyunlian.jinfu.store.dto.req;

import com.xinyunlian.jinfu.bank.enums.ECardType;

import java.io.Serializable;

/**
 * Created by KimLL on 2016/8/18.
 */

public class CertifyDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;

    private String mobile;

    private String userName;

    private String idCardNo;

    private String bankCardNo;

    private String idCardPic1Base64;

    private String idCardPic2Base64;

    private String logLng;

    private String logLat;

    private String logAddress;

    private Long bankId;

    //1-借记卡 2-信用卡
    private ECardType cardType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getIdCardPic1Base64() {
        return idCardPic1Base64;
    }

    public void setIdCardPic1Base64(String idCardPic1Base64) {
        this.idCardPic1Base64 = idCardPic1Base64;
    }

    public String getIdCardPic2Base64() {
        return idCardPic2Base64;
    }

    public void setIdCardPic2Base64(String idCardPic2Base64) {
        this.idCardPic2Base64 = idCardPic2Base64;
    }

    public String getLogLng() {
        return logLng;
    }

    public void setLogLng(String logLng) {
        this.logLng = logLng;
    }

    public String getLogLat() {
        return logLat;
    }

    public void setLogLat(String logLat) {
        this.logLat = logLat;
    }

    public String getLogAddress() {
        return logAddress;
    }

    public void setLogAddress(String logAddress) {
        this.logAddress = logAddress;
    }

    public ECardType getCardType() {
        return cardType;
    }

    public void setCardType(ECardType cardType) {
        this.cardType = cardType;
    }
}
