package com.xinyunlian.jinfu.order.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.order.enums.ECmccOrderTradeStatus;
import com.xinyunlian.jinfu.order.enums.converters.ECmccOrderTradeStatusConverter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by menglei on 2016年11月20日.
 */
@Entity
@Table(name = "cmcc_trade_record")
@EntityListeners(IdInjectionEntityListener.class)
public class CmccTradeRecordPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CMCC_TRADE_NO")
    private String cmccTradeNo;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    @Column(name = "BANK_CARD_NO")
    private String bankCardNo;

    @Column(name = "BANK_CARD_NAME")
    private String bankCardName;

    @Column(name = "BANK_CODE")
    private String bankCode;

    @Column(name = "TRADE_STATUS")
    @Convert(converter = ECmccOrderTradeStatusConverter.class)
    private ECmccOrderTradeStatus tradeStatus;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ECmccOrderTradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(ECmccOrderTradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
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
