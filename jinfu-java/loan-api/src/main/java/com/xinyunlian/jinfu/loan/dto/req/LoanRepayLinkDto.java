package com.xinyunlian.jinfu.loan.dto.req;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2016/11/15.
 */
public class LoanRepayLinkDto implements Serializable{

    private String repayId;

    private String scheduleId;

    private BigDecimal capital;

    private BigDecimal interest;

    private BigDecimal fine;

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
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
