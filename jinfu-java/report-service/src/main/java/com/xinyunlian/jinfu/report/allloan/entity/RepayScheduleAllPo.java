package com.xinyunlian.jinfu.report.allloan.entity;

import com.xinyunlian.jinfu.report.allloan.enums.EScheduleStatus;
import com.xinyunlian.jinfu.report.allloan.enums.converter.EScheduleStatusEnumConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dell on 2016/11/7.
 */
@Entity
@Table(name = "fp_repay_schd_all")
public class RepayScheduleAllPo implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    @Column(name = "SCHD_ID")
    private String scheduleId;

    @Column(name = "LOAN_ID")
    private String loanId;

    @Column(name = "LOAN_NAME")
    private String loanName;

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

    @Convert(converter = EScheduleStatusEnumConverter.class)
    @Column(name = "STATUS")
    private EScheduleStatus scheduleStatus;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "area")
    private String area;

    @Column(name = "USER_NAME")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
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
    }

    public BigDecimal getShouldCapital() {
        return shouldCapital;
    }

    public void setShouldCapital(BigDecimal shouldCapital) {
        this.shouldCapital = shouldCapital;
    }

    public BigDecimal getActualCapital() {
        return actualCapital;
    }

    public void setActualCapital(BigDecimal actualCapital) {
        this.actualCapital = actualCapital;
    }

    public BigDecimal getShouldInterest() {
        return shouldInterest;
    }

    public void setShouldInterest(BigDecimal shouldInterest) {
        this.shouldInterest = shouldInterest;
    }

    public BigDecimal getActualInterest() {
        return actualInterest;
    }

    public void setActualInterest(BigDecimal actualInterest) {
        this.actualInterest = actualInterest;
    }

    public BigDecimal getShouldFineCapital() {
        return shouldFineCapital;
    }

    public void setShouldFineCapital(BigDecimal shouldFineCapital) {
        this.shouldFineCapital = shouldFineCapital;
    }

    public BigDecimal getActualFineCapital() {
        return actualFineCapital;
    }

    public void setActualFineCapital(BigDecimal actualFineCapital) {
        this.actualFineCapital = actualFineCapital;
    }

    public BigDecimal getShouldFineInterest() {
        return shouldFineInterest;
    }

    public void setShouldFineInterest(BigDecimal shouldFineInterest) {
        this.shouldFineInterest = shouldFineInterest;
    }

    public BigDecimal getActualFineInterest() {
        return actualFineInterest;
    }

    public void setActualFineInterest(BigDecimal actualFineInterest) {
        this.actualFineInterest = actualFineInterest;
    }

    public BigDecimal getShouldFee() {
        return shouldFee;
    }

    public void setShouldFee(BigDecimal shouldFee) {
        this.shouldFee = shouldFee;
    }

    public BigDecimal getActualFee() {
        return actualFee;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
