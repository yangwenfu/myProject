package com.xinyunlian.jinfu.coupon.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/1/11.
 */
public class LoanCouponCalcDto implements Serializable{

    /**
     * 可用优惠券数量
     */
    private Long count;

    /**
     * 扣减金额
     */
    private BigDecimal amt;

    /**
     * 参与计算的优惠券ID
     * @return
     */
    private Long couponId;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }
}