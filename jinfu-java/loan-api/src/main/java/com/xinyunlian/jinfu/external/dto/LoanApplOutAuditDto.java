package com.xinyunlian.jinfu.external.dto;

import com.xinyunlian.jinfu.external.enums.PaymentOptionType;
import com.xinyunlian.jinfu.loan.enums.EApplOutAuditType;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 */
public class LoanApplOutAuditDto implements Serializable{

    private String id;

    private String applId;

    private EApplOutType type;

    private String termLen;

    private BigDecimal loanAmt;

    private EApplOutAuditType auditType;

    private PaymentOptionType paymentOption;

    private String reason;

    private BigDecimal commissions;

    public PaymentOptionType getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(PaymentOptionType paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public EApplOutType getType() {
        return type;
    }

    public void setType(EApplOutType type) {
        this.type = type;
    }

    public String getTermLen() {
        return termLen;
    }

    public void setTermLen(String termLen) {
        this.termLen = termLen;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public EApplOutAuditType getAuditType() {
        return auditType;
    }

    public void setAuditType(EApplOutAuditType auditType) {
        this.auditType = auditType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getCommissions() {
        return commissions;
    }

    public void setCommissions(BigDecimal commissions) {
        this.commissions = commissions;
    }
}
