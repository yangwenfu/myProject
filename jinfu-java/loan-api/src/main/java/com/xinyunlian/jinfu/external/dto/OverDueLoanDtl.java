package com.xinyunlian.jinfu.external.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by godslhand on 2017/6/19.
 */
public class OverDueLoanDtl implements Serializable {

    private Double loanAmount; //贷款金额
    private Double balance;//总未还金额
    private List<RefundDto> refunds;// 还款计划

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<RefundDto> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<RefundDto> refunds) {
        this.refunds = refunds;
    }
}
