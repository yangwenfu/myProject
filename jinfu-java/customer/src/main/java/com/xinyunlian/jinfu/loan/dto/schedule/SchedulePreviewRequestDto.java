package com.xinyunlian.jinfu.loan.dto.schedule;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/1/10.
 */
public class SchedulePreviewRequestDto implements Serializable {

    private BigDecimal amt;

    private Integer period;

    private String productId;

    private BigDecimal feeRt;

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getFeeRt() {
        return feeRt;
    }

    public void setFeeRt(BigDecimal feeRt) {
        this.feeRt = feeRt;
    }
}
