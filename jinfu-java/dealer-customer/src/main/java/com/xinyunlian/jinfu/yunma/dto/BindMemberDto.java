package com.xinyunlian.jinfu.yunma.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 资料补全
 *
 * @author menglei
 */

public class BindMemberDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long storeId;

    private Long bankCardId;//银行卡id

    private String bankCardPicBase64;//银行卡照

    private String qrCodeNo;

    private String qrCodeUrl;

    private String logLng;

    private String logLat;

    private String logAddress;

    private Integer bankCardType;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getBankCardPicBase64() {
        return bankCardPicBase64;
    }

    public void setBankCardPicBase64(String bankCardPicBase64) {
        this.bankCardPicBase64 = bankCardPicBase64;
    }

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
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

    public Integer getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(Integer bankCardType) {
        this.bankCardType = bankCardType;
    }
}


