package com.xinyunlian.jinfu.external.dto.resp;

import java.io.Serializable;

/**
 * Created by godslhand on 2017/6/22.
 */
public class LoanRefundsNotify implements Serializable{
    public static final int RESULT_SUCCCESS =1;
    private String loanId;//贷款编号
    private Integer periodNumber;//还款期数序号
    private Integer result;
    private String reason;
    private String date; //还款日期(YYYYMMDD)
    private Double amount;//还款总金额=本金+利息+手续费+罚息
    private Double refundCapital;//还款本金
    private Double refundInterest;//还款利息
    private Double refundCommission;//还款手续费
    private Double refundDefaultInterest; //还款罚息
    private Integer refundFlag;//此期是否还款完毕 1：已还完完毕    2：未还款完毕
    private Integer refundType;//还款类型 1：到期还款，2：提前还款，3：追偿还款

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Integer getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(Integer periodNumber) {
        this.periodNumber = periodNumber;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRefundCapital() {
        return refundCapital;
    }

    public void setRefundCapital(Double refundCapital) {
        this.refundCapital = refundCapital;
    }

    public Double getRefundInterest() {
        return refundInterest;
    }

    public void setRefundInterest(Double refundInterest) {
        this.refundInterest = refundInterest;
    }

    public Double getRefundCommission() {
        return refundCommission;
    }

    public void setRefundCommission(Double refundCommission) {
        this.refundCommission = refundCommission;
    }

    public Double getRefundDefaultInterest() {
        return refundDefaultInterest;
    }


    public void setRefundDefaultInterest(Double refundDefaultInterest) {
        this.refundDefaultInterest = refundDefaultInterest;
    }

    public Integer getRefundFlag() {
        return refundFlag;
    }

    public void setRefundFlag(Integer refundFlag) {
        this.refundFlag = refundFlag;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }


}
