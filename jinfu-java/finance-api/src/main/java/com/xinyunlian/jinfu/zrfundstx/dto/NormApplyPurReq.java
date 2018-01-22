package com.xinyunlian.jinfu.zrfundstx.dto;

import java.math.BigDecimal;

/**
 * 个人普通申购请求dto
 * Created by dongfangchao on 2016/11/21.
 */
public class NormApplyPurReq extends BaseReq {
    private static final long serialVersionUID = 7215755655665600854L;

    private String TransactionAccountID;

    private String ApplicationNo;

    private String FundCode;

    private BigDecimal ApplicationAmount;

    private Long ShareClass;

    private Long AutoFrozen;

    private String TradeBusinType;

    private String IgnoreRisk;

    private BigDecimal CouponAmount;

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

    public Long getAutoFrozen() {
        return AutoFrozen;
    }

    public void setAutoFrozen(Long autoFrozen) {
        AutoFrozen = autoFrozen;
    }

    public String getTradeBusinType() {
        return TradeBusinType;
    }

    public void setTradeBusinType(String tradeBusinType) {
        TradeBusinType = tradeBusinType;
    }

    public String getIgnoreRisk() {
        return IgnoreRisk;
    }

    public void setIgnoreRisk(String ignoreRisk) {
        IgnoreRisk = ignoreRisk;
    }

    public BigDecimal getApplicationAmount() {
        return ApplicationAmount;
    }

    public void setApplicationAmount(BigDecimal applicationAmount) {
        ApplicationAmount = applicationAmount;
    }

    public BigDecimal getCouponAmount() {
        return CouponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        CouponAmount = couponAmount;
    }

    public BigDecimal getDiscount() {
        return Discount;
    }

    public void setDiscount(BigDecimal discount) {
        Discount = discount;
    }
}
