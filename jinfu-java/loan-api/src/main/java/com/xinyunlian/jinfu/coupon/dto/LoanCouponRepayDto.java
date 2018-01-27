package com.xinyunlian.jinfu.coupon.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/1/11.
 */
public class LoanCouponRepayDto implements Serializable{

    /**
     * 优惠券类型
     */
    private String couponType;

    /**
     * 优惠券金额
     */
    private BigDecimal price;

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}