package com.xinyunlian.jinfu.schedule.dto.management;

import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 */
public class MScheduleDto implements Serializable{

    private String scheduleId;

    private Integer period;

    private BigDecimal capital;

    private BigDecimal interest;

    private String date;

    //是否可以手动代扣
    private Boolean canManual;

    private int overdue;

    private EScheduleStatus status;

    /**
     * 指令
     */
    private PayRecvOrdDto payRecv;

    public PayRecvOrdDto getPayRecv() {
        return payRecv;
    }

    public void setPayRecv(PayRecvOrdDto payRecv) {
        this.payRecv = payRecv;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOverdue() {
        return overdue;
    }

    public void setOverdue(int overdue) {
        this.overdue = overdue;
    }

    public EScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(EScheduleStatus status) {
        this.status = status;
    }

    public Boolean getCanManual() {
        return canManual;
    }

    public void setCanManual(Boolean canManual) {
        this.canManual = canManual;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
}
