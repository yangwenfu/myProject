package com.xinyunlian.jinfu.repay.dto;

import com.xinyunlian.jinfu.repay.ERefundFlag;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 */
public class ExternalRepayCallbackDto implements Serializable{

    private String applId;

    private Integer period;

    private Boolean result;

    private String reason;

    /**
     * 还款时间
     * yyyymmdd
     */
    private String date;

    private BigDecimal amount;

    private BigDecimal capital;

    private BigDecimal interest;

    private BigDecimal fee;

    private BigDecimal fine;

    /**
     * 是否已还款完毕
     */
    private ERefundFlag refundFlag;

    /**
     * 还款类型
     1：到期还款，2：提前还款，3：追偿还款
     */
    private Integer refundType;

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
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

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public ERefundFlag getRefundFlag() {
        return refundFlag;
    }

    public void setRefundFlag(ERefundFlag refundFlag) {
        this.refundFlag = refundFlag;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }
}
