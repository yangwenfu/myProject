package com.xinyunlian.jinfu.zrfundstx.dto;

import java.math.BigDecimal;

/**
 * T+0（实时）赎回请求dto
 * Created by dongfangchao on 2016/11/21.
 */
public class RedeemRealTimeReq extends BaseReq{
    private static final long serialVersionUID = -7159279915191905090L;

    private String TransactionAccountID;

    private String ApplicationNo;

    private String FundCode;

    private BigDecimal ApplicationVol;

    private Long ShareClass;

    private String TradeBusinType;

    public String getTransactionAccountID() {
        return TransactionAccountID;
    }

    public void setTransactionAccountID(String transactionAccountID) {
        TransactionAccountID = transactionAccountID;
    }

    public String getApplicationNo() {
        return ApplicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        ApplicationNo = applicationNo;
    }

    public String getFundCode() {
        return FundCode;
    }

    public void setFundCode(String fundCode) {
        FundCode = fundCode;
    }

    public BigDecimal getApplicationVol() {
        return ApplicationVol;
    }

    public void setApplicationVol(BigDecimal applicationVol) {
        ApplicationVol = applicationVol;
    }

    public Long getShareClass() {
        return ShareClass;
    }

    public void setShareClass(Long shareClass) {
        ShareClass = shareClass;
    }

    public String getTradeBusinType() {
        return TradeBusinType;
    }

    public void setTradeBusinType(String tradeBusinType) {
        TradeBusinType = tradeBusinType;
    }
}
