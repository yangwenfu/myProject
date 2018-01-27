package com.xinyunlian.jinfu.overdue.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/1/13.
 */
public class OverdueMonthDetailDto implements Serializable{

    /**
     * 本期应还金额
     */
    private BigDecimal should;

    /**
     * 应还本金
     */
    private BigDecimal capital;

    /**
     * 应还利息
     */
    private BigDecimal interest;

    /**
     * 产生罚息
     */
    private BigDecimal fine;

    /**
     * 逾期天数
     */
    private Integer days;

    /**
     * 还款日
     */
    private String repayDate;

    /**
     * 期数
     */
    private Integer period;

    /**
     * 总期数
     */
    private Integer totalPeriod;

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getShould() {
        return should;
    }

    public void setShould(BigDecimal should) {
        this.should = should;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(Integer totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    @Override
    public String toString() {
        return "OverdueMonthDetailDto{" +
                "should=" + should +
                ", capital=" + capital +
                ", interest=" + interest +
                ", fine=" + fine +
                ", days=" + days +
                ", repayDate='" + repayDate + '\'' +
                ", period=" + period +
                ", totalPeriod=" + totalPeriod +
                '}';
    }
}
