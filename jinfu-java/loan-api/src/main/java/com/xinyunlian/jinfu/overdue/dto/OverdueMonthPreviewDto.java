package com.xinyunlian.jinfu.overdue.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/1/13.
 */
public class OverdueMonthPreviewDto implements Serializable{

    /**
     * 本期应还总金额
     */
    private BigDecimal sum;

    /**
     * 应还本金
     */
    private BigDecimal capital;

    /**
     * 应还利息
     */
    private BigDecimal interest;

    /**
     * 应还罚息
     */
    private BigDecimal fine;

    /**
     * 还款后剩余应还
     */
    private BigDecimal surplus;

    /**
     * 期数
     */
    private Integer period;

    /**
     * 总期数
     */
    private Integer totalPeriod;

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getSurplus() {
        return surplus;
    }

    public void setSurplus(BigDecimal surplus) {
        this.surplus = surplus;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
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
}
