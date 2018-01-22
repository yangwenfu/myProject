package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dongfangchao on 2017/1/3/0003.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "asset")
public class SuperCashTradeQuerySubResp implements Serializable {
    private static final long serialVersionUID = -3051035404556004578L;

    @XmlElement(name = "AppSheetSerialNo")
    private Long appSheetSerialNo;
    @XmlElement(name = "TransactionDate")
    private Long transactionDate;
    @XmlElement(name = "Transactiontime")
    private Long transactiontime;
    @XmlElement(name = "FundCode")
    private String fundCode;
    @XmlElement(name = "ApplicationAmount")
    private BigDecimal applicationAmount;
    @XmlElement(name = "ConfirmFlag")
    private Integer confirmFlag;
    @XmlElement(name = "PayFlag")
    private Integer payFlag;
    @XmlElement(name = "TradeDate")
    private Long tradeDate;
    @XmlElement(name = "BusinessCode")
    private Long businessCode;

    public Long getAppSheetSerialNo() {
        return appSheetSerialNo;
    }

    public void setAppSheetSerialNo(Long appSheetSerialNo) {
        this.appSheetSerialNo = appSheetSerialNo;
    }

    public Long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getTransactiontime() {
        return transactiontime;
    }

    public void setTransactiontime(Long transactiontime) {
        this.transactiontime = transactiontime;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public BigDecimal getApplicationAmount() {
        return applicationAmount;
    }

    public void setApplicationAmount(BigDecimal applicationAmount) {
        this.applicationAmount = applicationAmount;
    }

    public Integer getConfirmFlag() {
        return confirmFlag;
    }

    public void setConfirmFlag(Integer confirmFlag) {
        this.confirmFlag = confirmFlag;
    }

    public Integer getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(Integer payFlag) {
        this.payFlag = payFlag;
    }

    public Long getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Long tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Long getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(Long businessCode) {
        this.businessCode = businessCode;
    }
}
