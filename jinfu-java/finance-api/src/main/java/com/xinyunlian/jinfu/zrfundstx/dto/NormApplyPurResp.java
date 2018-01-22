package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * 个人普通申购应答dto
 * Created by dongfangchao on 2016/11/21.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
public class NormApplyPurResp extends BaseResp {

    private static final long serialVersionUID = -7150153047856116089L;

    @XmlElement(name = "ApplicationNo")
    private String applicationNo;
    @XmlElement(name = "AppSheetSerialNo")
    private Long appSheetSerialNo;
    @XmlElement(name = "TransactionDate")
    private Long transactionDate;
    @XmlElement(name = "Transactiontime")
    private Long transactiontime;
    @XmlElement(name = "AvailableVol")
    private BigDecimal availableVol;
    @XmlElement(name = "TradeDate")
    private Long tradeDate;

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

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

    public BigDecimal getAvailableVol() {
        return availableVol;
    }

    public void setAvailableVol(BigDecimal availableVol) {
        this.availableVol = availableVol;
    }

    public Long getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Long tradeDate) {
        this.tradeDate = tradeDate;
    }
}
