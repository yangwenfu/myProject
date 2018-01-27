package com.xinyunlian.jinfu.loan.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 还款-还款计划关联表
 */
@Entity
@Table(name = "LINK_REPAY_SCHD")
@EntityListeners(IdInjectionEntityListener.class)
public class LinkRepaySchdPo extends BaseMaintainablePo {

    @Id
    @Column(name = "ID")
    private String jnlNo;

    @Column(name = "REPAY_ID")
    private String repayId;

    @Column(name = "SCHEDULE_ID")
    private String schdId;

    @Column(name = "CAPITAL")
    private BigDecimal capital;

    @Column(name = "INTEREST")
    private BigDecimal interest;

    @Column(name = "FINE")
    private BigDecimal fine;

    public String getJnlNo() {
        return jnlNo;
    }

    public void setJnlNo(String jnlNo) {
        this.jnlNo = jnlNo;
    }

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

    public String getSchdId() {
        return schdId;
    }

    public void setSchdId(String schdId) {
        this.schdId = schdId;
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

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }
}