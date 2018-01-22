package com.xinyunlian.jinfu.coupon.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/3/28.
 */
public class LoanMCouponDto implements Serializable {

    //类型，名称，券码
    private String desc;

    //券金额
    private BigDecimal price;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
