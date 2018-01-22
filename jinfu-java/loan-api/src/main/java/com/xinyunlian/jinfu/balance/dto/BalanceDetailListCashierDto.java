package com.xinyunlian.jinfu.balance.dto;

import com.xinyunlian.jinfu.balance.enums.EBalanceStatus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/5/23.
 */
public class BalanceDetailListCashierDto implements Serializable{

    private Long id;

    private EBalanceStatus balanceStatus;

    private String payDate;

    private String bizId;

    private String payStatus;

    private String channelName;

    private String balanceDate;

    private BigDecimal payAmt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public EBalanceStatus getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(EBalanceStatus balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(String balanceDate) {
        this.balanceDate = balanceDate;
    }
}
