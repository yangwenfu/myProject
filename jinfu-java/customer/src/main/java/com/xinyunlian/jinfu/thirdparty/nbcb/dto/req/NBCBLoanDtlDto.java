package com.xinyunlian.jinfu.thirdparty.nbcb.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bright on 2017/5/18.
 */
public class NBCBLoanDtlDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("id_no")
    private String idNo;

    @JsonProperty("order_no")
    private String orderNo;

    @JsonProperty("order_status")
    private String orderStatus;

    @JsonProperty("credit_dead_line")
    private String creditDeadLine;

    @JsonProperty("credit_status")
    private String creditStatus;

    @JsonProperty("credit")
    private String credit;

    @JsonProperty("loan_remaining")
    private String loanRemaining;

    @JsonProperty("loan_remaining_avg")
    private String loanRemainingAvg;

    @JsonProperty("loan_status")
    private String loanStatus;

    @JsonProperty("modify_ts")
    private String modifyTs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCreditDeadLine() {
        return creditDeadLine;
    }

    public void setCreditDeadLine(String creditDeadLine) {
        this.creditDeadLine = creditDeadLine;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getLoanRemaining() {
        return loanRemaining;
    }

    public void setLoanRemaining(String loanRemaining) {
        this.loanRemaining = loanRemaining;
    }

    public String getLoanRemainingAvg() {
        return loanRemainingAvg;
    }

    public void setLoanRemainingAvg(String loanRemainingAvg) {
        this.loanRemainingAvg = loanRemainingAvg;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getModifyTs() {
        return modifyTs;
    }

    public void setModifyTs(String modifyTs) {
        this.modifyTs = modifyTs;
    }
}
