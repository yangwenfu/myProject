package com.xinyunlian.jinfu.order.dto;

import com.xinyunlian.jinfu.order.enums.ECmccOrderTradeStatus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by menglei on 2016年11月20日.
 */
public class CmccTradeRecordDto implements Serializable {
    private static final long serialVersionUID = -6375635388364584073L;

    private String cmccTradeNo;

    private Long storeId;

    private BigDecimal amount;

    private String idCardNo;

    private String bankCardNo;

    private String bankCardName;

    private String bankCode;

    private ECmccOrderTradeStatus tradeStatus;

    private String createTime;

    public String getCmccTradeNo() {
        return cmccTradeNo;
    }

    public void setCmccTradeNo(String cmccTradeNo) {
        this.cmccTradeNo = cmccTradeNo;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public ECmccOrderTradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(ECmccOrderTradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
}
