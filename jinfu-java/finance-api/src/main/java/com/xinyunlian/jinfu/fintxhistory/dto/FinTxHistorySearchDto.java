package com.xinyunlian.jinfu.fintxhistory.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxStatus;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public class FinTxHistorySearchDto extends PagingDto<FinTxHistoryDto> {

    private static final long serialVersionUID = -9162082105861315397L;

    private String finTxId;

    private String userId;

    private String extTxAccId;

    private String extTxId;

    private Date orderDate;

    private Date orderDateFrom;

    private Date orderDateTo;

    private ETxType txType;

    private String finFundId;

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

    public String getFinFundId() {
        return finFundId;
    }

    public void setFinFundId(String finFundId) {
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

    public Date getOrderDateFrom() {
        return orderDateFrom;
    }

    public void setOrderDateFrom(Date orderDateFrom) {
        this.orderDateFrom = orderDateFrom;
    }

    public Date getOrderDateTo() {
        return orderDateTo;
    }

    public void setOrderDateTo(Date orderDateTo) {
        this.orderDateTo = orderDateTo;
    }

    public String getExtTxAccId() {
        return extTxAccId;
    }

    public void setExtTxAccId(String extTxAccId) {
        this.extTxAccId = extTxAccId;
    }
}
