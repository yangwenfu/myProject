package com.xinyunlian.jinfu.trade.dto;

import com.xinyunlian.jinfu.trade.enums.EBizCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by menglei on 2016-12-05.
 */
public class YmTradeDayDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dates;
    private String days;
    private String weeks;
    private EBizCode bizCode;
    private BigDecimal transAmt;
    private BigDecimal transFee;
    private Long count;

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public EBizCode getBizCode() {
        return bizCode;
    }

    public void setBizCode(EBizCode bizCode) {
        this.bizCode = bizCode;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public BigDecimal getTransFee() {
        return transFee;
    }

    public void setTransFee(BigDecimal transFee) {
        this.transFee = transFee;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}


