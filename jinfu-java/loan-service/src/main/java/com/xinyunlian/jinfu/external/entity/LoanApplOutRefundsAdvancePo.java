package com.xinyunlian.jinfu.external.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by godslhand on 2017/7/14.
 */
@Entity
@Table(name = "FP_LOAN_APPL_OUT_REFUNDSADVANCE")
public class LoanApplOutRefundsAdvancePo extends BaseMaintainablePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;
    @Column(name = "APPLY_ID")
    private String applyId;
    @Column(name = "OUT_REFUNDS_ADVANCE_ID")
    private String outRefundsAdvanceId;
    @Column(name = "BALANCE")
    private BigDecimal balance;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "CAPITAL")
    private BigDecimal capital;
    @Column(name = "INTEREST")
    private BigDecimal interest;
    @Column(name = "DISABLED" )
    private Boolean disabled =false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getOutRefundsAdvanceId() {
        return outRefundsAdvanceId;
    }

    public void setOutRefundsAdvanceId(String outRefundsAdvanceId) {
        this.outRefundsAdvanceId = outRefundsAdvanceId;
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
