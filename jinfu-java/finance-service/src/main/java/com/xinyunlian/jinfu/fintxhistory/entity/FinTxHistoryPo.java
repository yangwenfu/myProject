package com.xinyunlian.jinfu.fintxhistory.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxStatus;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxType;
import com.xinyunlian.jinfu.fintxhistory.enums.converter.ETxStatusConverter;
import com.xinyunlian.jinfu.fintxhistory.enums.converter.ETxTypeConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2016/11/21.
 */
@Entity
@Table(name = "fin_tx_history")
@EntityListeners(IdInjectionEntityListener.class)
public class FinTxHistoryPo extends BaseMaintainablePo {

    private static final long serialVersionUID = -2361216842873272699L;

    @Id
    @Column(name = "FIN_TX_ID")
    private String finTxId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "EXT_TX_ACC_ID")
    private String extTxAccId;

    @Column(name = "EXT_TX_ID")
    private String extTxId;

    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Column(name = "TX_TYPE")
    @Convert(converter = ETxTypeConverter.class)
    private ETxType txType;

    @Column(name = "FIN_FUND_ID")
    private Long finFundId;

    @Column(name = "TX_FEE")
    private BigDecimal txFee;

    @Column(name = "TX_STATUS")
    @Convert(converter = ETxStatusConverter.class)
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
