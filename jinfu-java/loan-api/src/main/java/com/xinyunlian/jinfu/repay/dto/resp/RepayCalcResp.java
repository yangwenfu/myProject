package com.xinyunlian.jinfu.repay.dto.resp;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2016/11/25.
 */
public class RepayCalcResp implements Serializable {

    /**
     * 应还本金
     */
    private BigDecimal capital;

    /**
     * 应还利息
     */
    private BigDecimal interest;

    /**
     * 应还罚息
     * @return
     */
    private BigDecimal fine;

    /**
     * 剩余应还本金
     */
    private BigDecimal surplus;

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public BigDecimal getFine() {
        return fine != null ? fine : BigDecimal.ZERO;
    }

    public BigDecimal getCapital() {
        return capital != null ? capital : BigDecimal.ZERO;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInterest() {
        return interest != null ? interest : BigDecimal.ZERO;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getSurplus() {
        return surplus != null ? surplus : BigDecimal.ZERO;
    }

    public void setSurplus(BigDecimal surplus) {
        this.surplus = surplus;
    }
}
