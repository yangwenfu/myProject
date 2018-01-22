package com.xinyunlian.jinfu.external.dto.req;

import java.io.Serializable;

public class LoanApplyReq implements Serializable{
    public String idNo;
    public Double amount;
    public Integer loanTerm;
    public Integer paymentOption;
    public String productId;
    public String loanPurpose;

    public String getIdNo() {
        return idNo;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public Integer getPaymentOption() {
        return paymentOption;
    }

    public String getProductId() {
        return productId;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public void setPaymentOption(Integer paymentOption) {
        this.paymentOption = paymentOption;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    @Override
    public String toString() {
        return "LoanApply [idNo=" + idNo + ", amount=" + amount + ", loanTerm=" + loanTerm + ", paymentOption="
                + paymentOption + ", productId=" + productId + ", loanPurpose=" + loanPurpose + "]";
    }


}
