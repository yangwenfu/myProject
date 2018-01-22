package com.xinyunlian.jinfu.audit.entity;

import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.audit.enums.converter.ELoanAuditStatusEnumConverter;
import com.xinyunlian.jinfu.audit.enums.converter.ELoanAuditTypeEnumConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.BasePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author willwang
 */
@Entity
@Table(name = "FP_LOAN_AUDIT")
@EntityListeners(IdInjectionEntityListener.class)
public class LoanAuditPo extends BaseMaintainablePo {

    @Id
    @Column(name = "AUDIT_ID")
    private String auditId;

    @Column(name = "APPL_ID")
    private String applId;

    @Convert(converter = ELoanAuditTypeEnumConverter.class)
    @Column(name = "AUDIT_TYPE")
    private EAuditType auditType;

    @Column(name = "LOAN_AMT")
    private BigDecimal loanAmt;

    @Column(name = "TERM_LEN")
    private Integer termLen;

    @Column(name = "REMARK")
    private String remark;

    @Convert(converter = ELoanAuditStatusEnumConverter.class)
    @Column(name = "STATUS")
    private EAuditStatus auditStatus;

    @Column(name = "REASON")
    private String reason;

    @Column(name = "TEMP")
    private Boolean temp;

    @Column(name = "AUDIT_USER_ID")
    private String auditUserId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AUDIT_DATE")
    private Date auditDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APPL_ID", insertable = false, updatable = false)
    private LoanApplPo loanApplPo;

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    public Boolean getTemp() {
        return temp;
    }

    public void setTemp(Boolean temp) {
        this.temp = temp;
    }

    public LoanApplPo getLoanApplPo() {
        return loanApplPo;
    }

    public void setLoanApplPo(LoanApplPo loanApplPo) {
        this.loanApplPo = loanApplPo;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public EAuditType getAuditType() {
        return auditType;
    }

    public void setAuditType(EAuditType auditType) {
        this.auditType = auditType;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public Integer getTermLen() {
        return termLen;
    }

    public void setTermLen(Integer termLen) {
        this.termLen = termLen;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public EAuditStatus getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(EAuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
