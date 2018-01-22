package com.xinyunlian.jinfu.schedule.dto;

import com.xinyunlian.jinfu.schedule.enums.ELoanCustomerStatus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 * 还款计划列表中的单一dto
 */
public class ScheduleListEachDto implements Serializable{

    private String date;

    private int period;

    private int totalPeriod;

    private BigDecimal average;

    private BigDecimal capital;

    private BigDecimal interest;

    private ELoanCustomerStatus status;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
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

    public ELoanCustomerStatus getStatus() {
        return status;
    }

    public void setStatus(ELoanCustomerStatus status) {
        this.status = status;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(int totalPeriod) {
        this.totalPeriod = totalPeriod;
    }
}
