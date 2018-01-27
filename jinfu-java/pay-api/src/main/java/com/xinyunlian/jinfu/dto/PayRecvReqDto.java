package com.xinyunlian.jinfu.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayRecvReqDto implements Serializable {

    private String idCardNo;

    private String bankCardNo;

    private String bankCardName;

    private String bankCode;

    private String tranNo;

    private BigDecimal trxAmt;

    private String trxMemo;

    private Boolean toPrivate;

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public BigDecimal getTrxAmt() {
        return trxAmt;
    }

    public void setTrxAmt(BigDecimal trxAmt) {
        this.trxAmt = trxAmt;
    }

    public String getTrxMemo() {
        return trxMemo;
    }

    public void setTrxMemo(String trxMemo) {
        this.trxMemo = trxMemo;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }


    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Boolean getToPrivate() {
        return toPrivate;
    }

    public void setToPrivate(Boolean toPrivate) {
        this.toPrivate = toPrivate;
    }
}
