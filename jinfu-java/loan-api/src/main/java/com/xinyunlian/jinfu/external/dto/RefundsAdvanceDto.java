package com.xinyunlian.jinfu.external.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by godslhand on 2017/6/19.
 */
public class RefundsAdvanceDto implements Serializable {

    private String loanRefuandInAdvanceId; //提前还款交易号
    private String applyId ;
    private BigDecimal balance;
    private BigDecimal amount;
    private BigDecimal capital;
    private BigDecimal interest;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getLoanRefuandInAdvanceId() {
        return loanRefuandInAdvanceId;
    }

    public void setLoanRefuandInAdvanceId(String loanRefuandInAdvanceId) {
        this.loanRefuandInAdvanceId = loanRefuandInAdvanceId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
}
