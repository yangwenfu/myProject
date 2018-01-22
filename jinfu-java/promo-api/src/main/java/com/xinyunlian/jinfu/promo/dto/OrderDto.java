package com.xinyunlian.jinfu.promo.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by jll on 2016/8/18.
 */

public class OrderDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String prodId;
    private String platform;
    private BigDecimal amount;
    private long orderTotal = -1;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(long orderTotal) {
        this.orderTotal = orderTotal;
    }
}
