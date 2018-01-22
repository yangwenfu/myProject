package com.xinyunlian.jinfu.schedule.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import com.xinyunlian.jinfu.schedule.enums.converter.EScheduleStatusEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author willwang
 */
@Entity
@Table(name = "FP_REPAY_SCHD")
public class SchedulePo extends BaseMaintainablePo {

    @Id
    @Column(name = "SCHD_ID")
    private String scheduleId;

    @Column(name = "LOAN_ID")
    private String loanId;

    @Column(name = "ACCT_NO")
    private String acctNo;

    @Column(name = "DUE_DT")
    private String dueDate;

    @Column(name = "PAY_DT")
    private String payDate;

    @Column(name = "SEQ_NO")
    private Integer seqNo;

    /**
     * 应还本金
     */
    @Column(name = "PRIN_PY_AMT")
    private BigDecimal shouldCapital;

    /**
     * 实还本金
     */
    @Column(name = "PRIN_PD_AMT")
    private BigDecimal actualCapital;

    /**
     * 应还利息
     */
    @Column(name = "INTR_PY_AMT")
    private BigDecimal shouldInterest;

    /**
     * 实还利息
     */
    @Column(name = "INTR_PD_AMT")
    private BigDecimal actualInterest;

    /**
     * 应还本金罚金
     */
    @Column(name = "PRIN_FINE_PY_AMT")
    private BigDecimal shouldFineCapital;

    /**
     * 实还本金罚金
     */
    @Column(name = "PRIN_FINE_PD_AMT")
    private BigDecimal actualFineCapital;

    /**
     * 应还利息罚金
     */
    @Column(name = "INTR_FINE_PY_AMT")
    private BigDecimal shouldFineInterest;

    /**
     * 实还利息罚金
     */
    @Column(name = "INTR_FINE_PD_AMT")
    private BigDecimal actualFineInterest;

    /**
     * 应还手续费
     */
    @Column(name = "FEE_PY_AMT")
    private BigDecimal shouldFee;

    /**
     * 已还手续费
     */
    @Column(name = "FEE_PD_AMT")
    private BigDecimal actualFee;

    @Column(name = "STATUS")
    @Convert(converter = EScheduleStatusEnumConverter.class)
    private EScheduleStatus scheduleStatus;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
        this.setScheduleId(String.format("%s_%s", this.getLoanId(), this.seqNo));
    }

    public BigDecimal getShouldCapital() {
        return shouldCapital != null ? shouldCapital : BigDecimal.ZERO;
    }

    public void setShouldCapital(BigDecimal shouldCapital) {
        this.shouldCapital = shouldCapital;
    }

    public BigDecimal getActualCapital() {
        return actualCapital != null ? actualCapital : BigDecimal.ZERO;
    }

    public void setActualCapital(BigDecimal actualCapital) {
        this.actualCapital = actualCapital;
    }

    public BigDecimal getShouldInterest() {
        return shouldInterest != null ? shouldInterest : BigDecimal.ZERO;
    }

    public void setShouldInterest(BigDecimal shouldInterest) {
        this.shouldInterest = shouldInterest;
    }

    public BigDecimal getActualInterest() {
        return actualInterest != null ? actualInterest : BigDecimal.ZERO;
    }

    public void setActualInterest(BigDecimal actualInterest) {
        this.actualInterest = actualInterest;
    }

    public BigDecimal getShouldFineCapital() {
        return shouldFineCapital != null ? shouldFineCapital : BigDecimal.ZERO;
    }

    public void setShouldFineCapital(BigDecimal shouldFineCapital) {
        this.shouldFineCapital = shouldFineCapital;
    }

    public BigDecimal getActualFineCapital() {
        return actualFineCapital != null ? actualFineCapital : BigDecimal.ZERO;
    }

    public void setActualFineCapital(BigDecimal actualFineCapital) {
        this.actualFineCapital = actualFineCapital;
    }

    public BigDecimal getShouldFineInterest() {
        return shouldFineInterest != null ? shouldFineInterest : BigDecimal.ZERO;
    }

    public void setShouldFineInterest(BigDecimal shouldFineInterest) {
        this.shouldFineInterest = shouldFineInterest;
    }

    public BigDecimal getActualFineInterest() {
        return actualFineInterest != null ? actualFineInterest : BigDecimal.ZERO;
    }

    public void setActualFineInterest(BigDecimal actualFineInterest) {
        this.actualFineInterest = actualFineInterest;
    }

    public BigDecimal getShouldFee() {
        return shouldFee != null ? shouldFee : BigDecimal.ZERO;
    }

    public void setShouldFee(BigDecimal shouldFee) {
        this.shouldFee = shouldFee;
    }

    public BigDecimal getActualFee() {
        return actualFee != null ? actualFee : BigDecimal.ZERO;
    }

    public void setActualFee(BigDecimal actualFee) {
        this.actualFee = actualFee;
    }

    public EScheduleStatus getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(EScheduleStatus scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }
}
