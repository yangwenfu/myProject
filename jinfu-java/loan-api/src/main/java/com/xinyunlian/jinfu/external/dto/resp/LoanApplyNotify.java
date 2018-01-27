package com.xinyunlian.jinfu.external.dto.resp;

import com.xinyunlian.jinfu.external.dto.RefundDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by godslhand on 2017/6/21.
 */
public class LoanApplyNotify implements Serializable{
    public static final int RESULT_SUCCCESS =1;
    private String loanId;
    private int result;
    private Double commissions;
    private Double loanAmount;
    private Integer loanTerm;
    private Integer paymentOption;
    private String reason;
    private List<RefundDto> refunds;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Double getCommissions() {
        return commissions;
    }

    public void setCommissions(Double commissions) {
        this.commissions = commissions;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public Integer getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(Integer paymentOption) {
        this.paymentOption = paymentOption;
    }

    public List<RefundDto> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<RefundDto> refunds) {
        this.refunds = refunds;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
