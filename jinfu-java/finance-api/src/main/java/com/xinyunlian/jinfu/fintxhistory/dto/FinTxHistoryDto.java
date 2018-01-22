package com.xinyunlian.jinfu.fintxhistory.dto;

import com.xinyunlian.jinfu.fintxhistory.enums.ETxStatus;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public class FinTxHistoryDto implements Serializable{

    private static final long serialVersionUID = 6485224347804979826L;

    private String finTxId;

    private String userId;

    private String extTxAccId;

    private String extTxId;

    private Date orderDate;

    private ETxType txType;

    private Long finFundId;

    private BigDecimal txFee;

    private ETxStatus txStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFinTxId() {
        return finTxId;
    }

    public void setFinTxId(String finTxId) {
        this.finTxId = finTxId;
    }

    public String getExtTxId() {
        return extTxId;
    }

    public void setExtTxId(String extTxId) {
        this.extTxId = extTxId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public ETxType getTxType() {
        return txType;
    }

    public void setTxType(ETxType txType) {
        this.txType = txType;
    }

    public Long getFinFundId() {
        return finFundId;
    }

    public void setFinFundId(Long finFundId) {
        this.finFundId = finFundId;
    }

    public BigDecimal getTxFee() {
        return txFee;
    }

    public void setTxFee(BigDecimal txFee) {
        this.txFee = txFee;
    }

    public ETxStatus getTxStatus() {
        return txStatus;
    }

    public void setTxStatus(ETxStatus txStatus) {
        this.txStatus = txStatus;
    }

    public String getExtTxAccId() {
        return extTxAccId;
    }

    public void setExtTxAccId(String extTxAccId) {
        this.extTxAccId = extTxAccId;
    }
}
