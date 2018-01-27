package com.xinyunlian.jinfu.finprofithistory.dto;

import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2016/11/21.
 */

public class FinProfitHistoryDto implements Serializable{

    private static final long serialVersionUID = 4396328788571707376L;
    private Long id;

    private String userId;

    private EFinOrg finOrg;

    private String extTxAccId;

    private Long finFundId;

    private BigDecimal profitAmt;

    private BigDecimal assetAmt;

    private BigDecimal totalProfit;

    private Date profitDate;

    private Date profitDateFrom;

    private Date profitDateTo;

    private BigDecimal yield;

    private BigDecimal foundIncome;

    private Date prodDetailUpdateDate;

    private String redeemDesc;

    private String finFundName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getProfitDate() {
        return profitDate;
    }

    public void setProfitDate(Date profitDate) {
        this.profitDate = profitDate;
    }

    public String getExtTxAccId() {
        return extTxAccId;
    }

    public void setExtTxAccId(String extTxAccId) {
        this.extTxAccId = extTxAccId;
    }

    public EFinOrg getFinOrg() {
        return finOrg;
    }

    public void setFinOrg(EFinOrg finOrg) {
        this.finOrg = finOrg;
    }

    public Long getFinFundId() {
        return finFundId;
    }

    public void setFinFundId(Long finFundId) {
        this.finFundId = finFundId;
    }

    public Date getProfitDateFrom() {
        return profitDateFrom;
    }

    public void setProfitDateFrom(Date profitDateFrom) {
        this.profitDateFrom = profitDateFrom;
    }

    public Date getProfitDateTo() {
        return profitDateTo;
    }

    public void setProfitDateTo(Date profitDateTo) {
        this.profitDateTo = profitDateTo;
    }

    public BigDecimal getProfitAmt() {
        return profitAmt;
    }

    public void setProfitAmt(BigDecimal profitAmt) {
        this.profitAmt = profitAmt;
    }

    public void setAssetAmt(BigDecimal assetAmt) {
        this.assetAmt = assetAmt;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public BigDecimal getFoundIncome() {
        return foundIncome;
    }

    public void setFoundIncome(BigDecimal foundIncome) {
        this.foundIncome = foundIncome;
    }

    public Date getProdDetailUpdateDate() {
        return prodDetailUpdateDate;
    }

    public void setProdDetailUpdateDate(Date prodDetailUpdateDate) {
        this.prodDetailUpdateDate = prodDetailUpdateDate;
    }

    public String getRedeemDesc() {
        return redeemDesc;
    }

    public void setRedeemDesc(String redeemDesc) {
        this.redeemDesc = redeemDesc;
    }

    public String getFinFundName() {
        return finFundName;
    }

    public void setFinFundName(String finFundName) {
        this.finFundName = finFundName;
    }

    public BigDecimal getAssetAmt() {
        return assetAmt;
    }
}
