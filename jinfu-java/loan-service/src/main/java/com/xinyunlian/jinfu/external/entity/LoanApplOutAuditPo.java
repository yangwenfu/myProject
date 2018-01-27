package com.xinyunlian.jinfu.external.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.external.enums.PaymentOptionType;
import com.xinyunlian.jinfu.external.enums.converter.EApplOutAuditTypeEnumConverter;
import com.xinyunlian.jinfu.external.enums.converter.EApplOutTypeEnumConverter;
import com.xinyunlian.jinfu.external.enums.converter.EPaymentOptionTypeEnumConverter;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.enums.EApplOutAuditType;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author willwang
 */
@Entity
@Table(name = "FP_LOAN_APPL_OUT_AUDIT")
public class LoanApplOutAuditPo extends BaseMaintainablePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "APPL_ID")
    private String applId;

    @Column(name = "stype")
    @Convert(converter = EApplOutTypeEnumConverter.class)
    private EApplOutType type;

    @Column(name = "TERM_LEN")
    private String termLen;

    @Column(name = "LOAN_AMT")
    private BigDecimal loanAmt;

    @Column(name = "paymentOption")
    @Convert(converter = EPaymentOptionTypeEnumConverter.class)
    private PaymentOptionType paymentOption;

    @Column(name = "RESULT")
    @Convert(converter = EApplOutAuditTypeEnumConverter.class)
    private EApplOutAuditType auditType;

    @Column(name = "REASON")
    private String reason;

    @Column(name = "commissions")
    private BigDecimal commissions;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APPL_ID", insertable = false, updatable = false)
    private LoanApplPo loanApplPo;

    public LoanApplPo getLoanApplPo() {
        return loanApplPo;
    }

    public void setLoanApplPo(LoanApplPo loanApplPo) {
        this.loanApplPo = loanApplPo;
    }

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
