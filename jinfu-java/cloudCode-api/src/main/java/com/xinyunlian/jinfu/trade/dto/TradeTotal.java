package com.xinyunlian.jinfu.trade.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by King on 2017/1/9.
 */
public class TradeTotal  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long tradeCount;
    private BigDecimal transAmtSum;
    private BigDecimal transFeeSum;

    public TradeTotal(Long tradeCount, BigDecimal transAmtSum, BigDecimal transFeeSum) {
        this.tradeCount = tradeCount;
        this.transAmtSum = transAmtSum;
        this.transFeeSum = transFeeSum;
    }

    public Long getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Long tradeCount) {
        this.tradeCount = tradeCount;
    }

    public BigDecimal getTransAmtSum() {
        return transAmtSum;
    }

    public void setTransAmtSum(BigDecimal transAmtSum) {
        this.transAmtSum = transAmtSum;
    }

    public BigDecimal getTransFeeSum() {
        return transFeeSum;
    }

    public void setTransFeeSum(BigDecimal transFeeSum) {
        this.transFeeSum = transFeeSum;
    }
}
