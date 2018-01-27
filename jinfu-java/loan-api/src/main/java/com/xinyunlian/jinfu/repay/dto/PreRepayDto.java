package com.xinyunlian.jinfu.repay.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 */
public class PreRepayDto implements Serializable{

    private BigDecimal repayAmt;

    private BigDecimal interest;

    private BigDecimal fee;

    public BigDecimal getRepayAmt() {
        return repayAmt != null ? repayAmt : BigDecimal.ZERO;
    }

    public void setRepayAmt(BigDecimal repayAmt) {
        this.repayAmt = repayAmt;
    }

    public BigDecimal getInterest() {
        return interest != null ? interest : BigDecimal.ZERO;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getFee() {
        return fee != null ? fee : BigDecimal.ZERO;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
