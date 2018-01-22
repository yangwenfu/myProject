package com.xinyunlian.jinfu.loan.dto.schedule;

import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/1/10.
 */
public class ScheduleResponseDto implements Serializable{

    /**
     * 当前期数
     */
    private Integer period;

    /**
     * 总期数
     */
    private Integer total;

    /**
     * 还款日
     */
    private String repayDate;

    /**
     * 应还本金
     */
    private BigDecimal capital;

    /**
     * 应还利息
     */
    private BigDecimal interest;

    /**
     * 还款计划状态
     */
    private EScheduleStatus status;

    public EScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(EScheduleStatus status) {
        this.status = status;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
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
