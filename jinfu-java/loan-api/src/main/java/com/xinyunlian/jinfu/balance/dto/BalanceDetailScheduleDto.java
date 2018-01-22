package com.xinyunlian.jinfu.balance.dto;

import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/5/23.
 */
public class BalanceDetailScheduleDto implements Serializable{

    private String scheduleId;

    private BigDecimal shouldTotal;

    private String dueDate;

    private String payDate;

    private EScheduleStatus status;

    private BigDecimal shouldCapital;

    private BigDecimal shouldInterest;

    private BigDecimal shouldFine;

    private BigDecimal shouldFee;

    private int fineDays;

    public int getFineDays() {
        return fineDays;
    }

    public void setFineDays(int fineDays) {
        this.fineDays = fineDays;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public BigDecimal getShouldTotal() {
        return this.getShouldCapital().add(this.getShouldInterest()).add(this.getShouldFine()).add(this.getShouldFee());
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public EScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(EScheduleStatus status) {
        this.status = status;
    }

    public BigDecimal getShouldCapital() {
        return shouldCapital;
    }

    public void setShouldCapital(BigDecimal shouldCapital) {
        this.shouldCapital = shouldCapital;
    }


    public BigDecimal getShouldInterest() {
        return shouldInterest;
    }

    public void setShouldInterest(BigDecimal shouldInterest) {
        this.shouldInterest = shouldInterest;
    }


    public BigDecimal getShouldFine() {
        return shouldFine;
    }

    public void setShouldFine(BigDecimal shouldFine) {
        this.shouldFine = shouldFine;
    }


    public BigDecimal getShouldFee() {
        return shouldFee;
    }

    public void setShouldFee(BigDecimal shouldFee) {
        this.shouldFee = shouldFee;
    }

}
