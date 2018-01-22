package com.xinyunlian.jinfu.zrfundstx.dto;

import java.math.BigDecimal;

/**
 * T+1（普通）赎回请求dto
 * Created by dongfangchao on 2016/11/21.
 */
public class RedeemNormReq extends BaseReq {
    private static final long serialVersionUID = 3330544125323668641L;

    private String TransactionAccountID;

    private String ApplicationNo;

    private String FundCode;

    private BigDecimal ApplicationVol;

    private Long ShareClass;

    private String TradeBusinType;

    private BigDecimal Discount;

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

    public BigDecimal getApplicationVol() {
        return ApplicationVol;
    }

    public void setApplicationVol(BigDecimal applicationVol) {
        ApplicationVol = applicationVol;
    }

    public BigDecimal getDiscount() {
        return Discount;
    }

    public void setDiscount(BigDecimal discount) {
        Discount = discount;
    }
}
