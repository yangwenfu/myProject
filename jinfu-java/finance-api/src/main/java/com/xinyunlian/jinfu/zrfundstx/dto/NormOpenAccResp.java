package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 个人普通开户应答dto
 * Created by dongfangchao on 2016/11/21.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
public class NormOpenAccResp extends BaseResp {
    private static final long serialVersionUID = -4923523711243591778L;

    @XmlElement(name = "ApplicationNo")
    private String applicationNo;
    @XmlElement(name = "AppSheetSerialNo")
    private Long appSheetSerialNo;
    @XmlElement(name = "TransactionAccountID")
    private String transactionAccountID;
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

    public String getTransactionAccountID() {
        return transactionAccountID;
    }

    public void setTransactionAccountID(String transactionAccountID) {
        this.transactionAccountID = transactionAccountID;
    }

    public Long getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Long tradeDate) {
        this.tradeDate = tradeDate;
    }
}
