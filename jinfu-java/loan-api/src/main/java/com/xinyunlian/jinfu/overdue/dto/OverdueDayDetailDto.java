package com.xinyunlian.jinfu.overdue.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/1/13.
 */
public class OverdueDayDetailDto implements Serializable{

    /**
     * 剩余未还本金
     */
    private BigDecimal surplus;

    /**
     * 产生罚息
     */
    private BigDecimal fine;

    /**
     * 逾期天数
     */
    private Integer days;

    public BigDecimal getSurplus() {
        return surplus;
    }

    public void setSurplus(BigDecimal surplus) {
        this.surplus = surplus;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
