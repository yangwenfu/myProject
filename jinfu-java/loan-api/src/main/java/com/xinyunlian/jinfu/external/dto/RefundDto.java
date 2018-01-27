package com.xinyunlian.jinfu.external.dto;

import java.io.Serializable;

/**
 * Created by godslhand on 2017/6/19.
 */
public class RefundDto implements Serializable{
    private String periodNumber; //还款期数序号
    private String dueDate;//还款日期
    private String dueAmount;//还款总金额
    private String dueCapital;//应还本金
    private String dueInterest;//应还利息
    private String status;//还款状态 1：已还 2：逾期    3：待还4：延期   5：提前

    private String defaultInterest ;//罚息



    public String getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(String periodNumber) {
        this.periodNumber = periodNumber;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getDueCapital() {
        return dueCapital;
    }

    public void setDueCapital(String dueCapital) {
        this.dueCapital = dueCapital;
    }

    public String getDueInterest() {
        return dueInterest;
    }

    public void setDueInterest(String dueInterest) {
        this.dueInterest = dueInterest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDefaultInterest() {
        return defaultInterest;
    }

    public void setDefaultInterest(String defaultInterest) {
        this.defaultInterest = defaultInterest;
    }

    @Override
    public String toString() {
        return "RefundDto{" +
                "periodNumber='" + periodNumber + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", dueAmount='" + dueAmount + '\'' +
                ", dueCapital='" + dueCapital + '\'' +
                ", dueInterest='" + dueInterest + '\'' +
                ", status='" + status + '\'' +
                ", defaultInterest='" + defaultInterest + '\'' +
                '}';
    }
}
