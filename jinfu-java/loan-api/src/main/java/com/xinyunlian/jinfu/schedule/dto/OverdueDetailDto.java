package com.xinyunlian.jinfu.schedule.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 */
public class OverdueDetailDto implements Serializable{

    private int period;

    private int totalPeriod;

    private String date;

    //剩余未还
    private BigDecimal surplus;

    private BigDecimal canCapital;

    private BigDecimal canInterest;

    private BigDecimal canFine;

    private BigDecimal surplusCapital;

    private BigDecimal surplusInterest;

    private BigDecimal surplusFine;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(int totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public BigDecimal getSurplus() {
        return surplus;
    }

    public void setSurplus(BigDecimal surplus) {
        this.surplus = surplus;
    }

    public BigDecimal getCanCapital() {
        return canCapital;
    }

    public void setCanCapital(BigDecimal canCapital) {
        this.canCapital = canCapital;
    }

    public BigDecimal getCanInterest() {
        return canInterest;
    }

    public void setCanInterest(BigDecimal canInterest) {
        this.canInterest = canInterest;
    }

    public BigDecimal getCanFine() {
        return canFine;
    }

    public void setCanFine(BigDecimal canFine) {
        this.canFine = canFine;
    }

    public BigDecimal getSurplusCapital() {
        return surplusCapital;
    }

    public void setSurplusCapital(BigDecimal surplusCapital) {
        this.surplusCapital = surplusCapital;
    }

    public BigDecimal getSurplusInterest() {
        return surplusInterest;
    }

    public void setSurplusInterest(BigDecimal surplusInterest) {
        this.surplusInterest = surplusInterest;
    }

    public BigDecimal getSurplusFine() {
        return surplusFine;
    }

    public void setSurplusFine(BigDecimal surplusFine) {
        this.surplusFine = surplusFine;
    }
}
