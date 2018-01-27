package com.xinyunlian.jinfu.overdue.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.overdue.enums.EOverdueInfoStatus;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

//@Entity
public class OverdueInfoPo extends BaseMaintainablePo {

    private String id;

    private String loanId;

    private String startDate;

    private String endDate;

    private BigDecimal overdueAmt;

    private BigDecimal fineRt;

    private BigDecimal fineAmt;

    private BigDecimal remitAmt;

    private Date repayDate;

    private Integer fineDays;

    private EOverdueInfoStatus overdueInfoStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getOverdueAmt() {
        return overdueAmt;
    }

    public void setOverdueAmt(BigDecimal overdueAmt) {
        this.overdueAmt = overdueAmt;
    }

    public BigDecimal getFineAmt() {
        return fineAmt;
    }

    public void setFineAmt(BigDecimal fineAmt) {
        this.fineAmt = fineAmt;
    }

    public BigDecimal getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(BigDecimal remitAmt) {
        this.remitAmt = remitAmt;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public Integer getFineDays() {
        return fineDays;
    }

    public void setFineDays(Integer fineDays) {
        this.fineDays = fineDays;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public EOverdueInfoStatus getOverdueInfoStatus() {
        return overdueInfoStatus;
    }

    public void setOverdueInfoStatus(EOverdueInfoStatus overdueInfoStatus) {
        this.overdueInfoStatus = overdueInfoStatus;
    }

    public BigDecimal getFineRt() {
        return fineRt;
    }

    public void setFineRt(BigDecimal fineRt) {
        this.fineRt = fineRt;
    }
}
