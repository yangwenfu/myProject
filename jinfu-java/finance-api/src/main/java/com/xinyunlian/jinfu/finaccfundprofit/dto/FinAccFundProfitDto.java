package com.xinyunlian.jinfu.finaccfundprofit.dto;

import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dongfangchao on 2016/11/23.
 */
public class FinAccFundProfitDto implements Serializable {

    private static final long serialVersionUID = 4711432249098848788L;

    private Long id;

    private String userId;

    private String extTxAccId;

    private Long finFundId;

    private BigDecimal holdAsset;

    private BigDecimal totalProfit;

    private EFinOrg finOrg;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getExtTxAccId() {
        return extTxAccId;
    }

    public void setExtTxAccId(String extTxAccId) {
        this.extTxAccId = extTxAccId;
    }

    public Long getFinFundId() {
        return finFundId;
    }

    public void setFinFundId(Long finFundId) {
        this.finFundId = finFundId;
    }

    public BigDecimal getHoldAsset() {
        return holdAsset;
    }

    public void setHoldAsset(BigDecimal holdAsset) {
        this.holdAsset = holdAsset;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public EFinOrg getFinOrg() {
        return finOrg;
    }

    public void setFinOrg(EFinOrg finOrg) {
        this.finOrg = finOrg;
    }
}
