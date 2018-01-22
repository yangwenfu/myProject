package com.xinyunlian.jinfu.finprofithistory.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2016/11/23.
 */
public class FinProfitHistorySumSearchDto extends PagingDto<FinProfitHistorySumDto> {

    private static final long serialVersionUID = 2804771925096385658L;

    private Long id;

    private String userId;

    private BigDecimal profitAmt;

    private Date profitDate;

    private Date profitDateFrom;

    private Date profitDateTo;

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

    public BigDecimal getProfitAmt() {
        return profitAmt;
    }

    public void setProfitAmt(BigDecimal profitAmt) {
        this.profitAmt = profitAmt;
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
}
