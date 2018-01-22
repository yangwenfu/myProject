package com.xinyunlian.jinfu.audit.dto;

import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.audit.enums.EAuditType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author willwang
 */
public class LoanAuditDto implements Serializable {

    private String auditId;

    private String applId;

    private BigDecimal loanAmt;

    private EAuditStatus auditStatus;

    private Integer period;

    private String reason;

    private String remark;

    private EAuditType auditType;

    private Date createDate;

    private Boolean temp;

    /**
     * 贷款总额
     */
    private BigDecimal loanAmount;

    /**
     * 贷款笔数
     */
    private Integer loanNum;

    /**
     * 月还款额
     */
    private BigDecimal repayMonth;

    /**
     * 审批人员编号
     */
    private String auditUserId;

    /**
     * 审批时间
     */
    private Date auditDate;

    /**
     * 审批人员名称
     */
    private String auditUsername;

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

    public String getAuditUsername() {
        return auditUsername;
    }

    public void setAuditUsername(String auditUsername) {
        this.auditUsername = auditUsername;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanNum() {
        return loanNum;
    }

    public void setLoanNum(Integer loanNum) {
        this.loanNum = loanNum;
    }

    public BigDecimal getRepayMonth() {
        return repayMonth;
    }

    public void setRepayMonth(BigDecimal repayMonth) {
        this.repayMonth = repayMonth;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Boolean getTemp() {
        return temp != null && temp;
    }

    public void setTemp(Boolean temp) {
        this.temp = temp;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public EAuditType getAuditType() {
        return auditType;
    }

    public void setAuditType(EAuditType auditType) {
        this.auditType = auditType;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public EAuditStatus getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(EAuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
