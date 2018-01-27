package com.xinyunlian.jinfu.dealer.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.dealer.enums.EDealerServiceFeeStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "DEALER_SERVICE_FEE")
@EntityListeners(IdInjectionEntityListener.class)
public class DealerServiceFeePo extends BaseMaintainablePo {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "LOAN_ID")
    private String loanId;

    @Column(name = "BANK_CARD_ID")
    private String bankCardId;

    @Column(name = "LOAN_AMT")
    private BigDecimal loanAmt;

    @Column(name = "SERVICE_FEE_RT")
    private BigDecimal serviceFeeRt;

    @Column(name = "SERVICE_FEE")
    private BigDecimal serviceFee;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS")
    private EDealerServiceFeeStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public BigDecimal getServiceFeeRt() {
        return serviceFeeRt;
    }

    public void setServiceFeeRt(BigDecimal serviceFeeRt) {
        this.serviceFeeRt = serviceFeeRt;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public EDealerServiceFeeStatus getStatus() {
        return status;
    }

    public void setStatus(EDealerServiceFeeStatus status) {
        this.status = status;
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }
}
