
package com.xinyunlian.jinfu.pay.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.pay.enums.converter.EOrdStatusEnumConverter;
import com.xinyunlian.jinfu.pay.enums.converter.EPrTypeEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "AC_PAY_RECV_ORD")
@EntityListeners(IdInjectionEntityListener.class)
public class PayRecvOrdPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ORD_ID")
    private String ordId;

    @Column(name = "ACCT_NO")
    private String acctNo;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "TRX_DT")
    private String trxDate;

    @Convert(converter = EPrTypeEnumConverter.class)
    @Column(name = "PR_TYPE")
    private EPrType prType;

    @Column(name = "BIZ_ID")
    private String bizId;

    @Column(name = "TRX_AMT")
    private BigDecimal trxAmt;

    @Column(name = "TRX_MEMO")
    private String trxMemo;

    @Column(name = "BANK_CARD_NO")
    private String bankCardNo;

    @Column(name = "BANK_CARD_NAME")
    private String bankCardName;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RSP_TS")
    private Date rspDate;

    @Convert(converter = EOrdStatusEnumConverter.class)
    @Column(name = "ORD_STATUS")
    private EOrdStatus ordStatus;

    @Column(name = "RET_CODE")
    private String retCode;

    @Column(name = "RET_MSG")
    private String retMsg;

    @Column(name = "RET_CHANNEL_CODE")
    private String retChannelCode;

    @Column(name = "RET_CHANNEL_NAME")
    private String retChannelName;

    @Column(name = "CREDIT_LINE_RSRV_ID")
    private String creditLineRsrvId;

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

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
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
}
