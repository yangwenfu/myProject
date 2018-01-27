package com.xinyunlian.jinfu.pay.dto;

import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PayRecvOrdDto implements Serializable{

    private static final long serialVersionUID = 1L;

    private String ordId;

    private String acctNo;

    private String userId;

    private String trxDate;

    private EPrType prType;

    private String bizId;

    private BigDecimal trxAmt;

    private String trxMemo;

    private String bankCardNo;

    private String bankCardName;

    private String bankName;

    private Date rspDate;

    private EOrdStatus ordStatus;

    private String retCode;

    private String retMsg;

    private String retChannelCode;

    private String retChannelName;

    private String creditLineRsrvId;

    private Date createDateTime;

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getRetChannelCode() {
        return retChannelCode;
    }

    public void setRetChannelCode(String retChannelCode) {
        this.retChannelCode = retChannelCode;
    }

    public String getRetChannelName() {
        return retChannelName;
    }

    public void setRetChannelName(String retChannelName) {
        this.retChannelName = retChannelName;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(String trxDate) {
        this.trxDate = trxDate;
    }

    public EPrType getPrType() {
        return prType;
    }

    public void setPrType(EPrType prType) {
        this.prType = prType;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Date getRspDate() {
        return rspDate;
    }

    public void setRspDate(Date rspDate) {
        this.rspDate = rspDate;
    }

    public EOrdStatus getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(EOrdStatus ordStatus) {
        this.ordStatus = ordStatus;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreditLineRsrvId() {
        return creditLineRsrvId;
    }

    public void setCreditLineRsrvId(String creditLineRsrvId) {
        this.creditLineRsrvId = creditLineRsrvId;
    }

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }
}
