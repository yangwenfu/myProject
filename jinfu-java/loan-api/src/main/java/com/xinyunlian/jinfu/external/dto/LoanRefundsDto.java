package com.xinyunlian.jinfu.external.dto;

import java.math.BigDecimal;

/**
 * Created by godslhand on 2017/7/13.
 */
public class LoanRefundsDto {

    private String applyId;//贷款编号
    private Integer periodNumber;//还款期数序号
    private Integer result;
    private String reason;
    private String date; //还款日期
    private BigDecimal amount;//还款总金额=本金+利息+手续费+罚息
    private BigDecimal refundCapital;//还款本金
    private BigDecimal refundInterest;//还款利息
    private BigDecimal refundCommission;//还款手续费
    private BigDecimal refundDefaultInterest; //还款罚息
    private Integer refundFlag;//此期是否还款完毕 1：已还完完毕    2：未还款完毕
    private Integer refundType;//还款类型 1：到期还款，2：提前还款，3：追偿还款

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRefundCapital() {
        return refundCapital;
    }

    public void setRefundCapital(BigDecimal refundCapital) {
        this.refundCapital = refundCapital;
    }

    public BigDecimal getRefundInterest() {
        return refundInterest;
    }

    public void setRefundInterest(BigDecimal refundInterest) {
        this.refundInterest = refundInterest;
    }

    public BigDecimal getRefundCommission() {
        return refundCommission;
    }

    public void setRefundCommission(BigDecimal refundCommission) {
        this.refundCommission = refundCommission;
    }

    public BigDecimal getRefundDefaultInterest() {
        return refundDefaultInterest;
    }

    public void setRefundDefaultInterest(BigDecimal refundDefaultInterest) {
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

    @Override
    public String toString() {
        return "LoanRefundsDto{" +
                "applyId='" + applyId + '\'' +
                ", periodNumber=" + periodNumber +
                ", result=" + result +
                ", reason='" + reason + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", refundCapital=" + refundCapital +
                ", refundInterest=" + refundInterest +
                ", refundCommission=" + refundCommission +
                ", refundDefaultInterest=" + refundDefaultInterest +
                ", refundFlag=" + refundFlag +
                ", refundType=" + refundType +
                '}';
    }
}
