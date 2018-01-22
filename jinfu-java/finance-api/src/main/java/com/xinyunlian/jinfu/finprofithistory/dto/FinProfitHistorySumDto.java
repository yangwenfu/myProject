package com.xinyunlian.jinfu.finprofithistory.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2016/11/21.
 */

public class FinProfitHistorySumDto implements Serializable{

    private static final long serialVersionUID = 3222351905681404694L;

    private Long id;

    private String userId;

    private BigDecimal profitAmt;

    private BigDecimal assetAmt;

    private Date profitDate;

    private Date profitDateFrom;

    private Date profitDateTo;

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

    public BigDecimal getAssetAmt() {
        return assetAmt;
    }

    public void setAssetAmt(BigDecimal assetAmt) {
        this.assetAmt = assetAmt;
    }
}
