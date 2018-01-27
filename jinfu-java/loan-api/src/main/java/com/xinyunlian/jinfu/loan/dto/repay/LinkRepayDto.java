package com.xinyunlian.jinfu.loan.dto.repay;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Willwang on 2016/11/16.
 */
public class LinkRepayDto implements Serializable{

    private String jnlNo;

    private String repayId;

    private String schdId;

    private BigDecimal capital;

    private BigDecimal interest;

    private BigDecimal fine;

    private Date createTs;

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

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
