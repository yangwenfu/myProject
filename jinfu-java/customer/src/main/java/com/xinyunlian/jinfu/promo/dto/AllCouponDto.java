package com.xinyunlian.jinfu.promo.dto;

import java.util.List;

/**
 * Created by King on 2017/3/27.
 */
public class AllCouponDto {
    private long unusedCount;
    private long usedCount;
    private long overdueCount;
    private List<CouponDto> unusedCoupons;
    private List<CouponDto> usedCoupons;
    private List<CouponDto> overdueCoupons;

    public long getUnusedCount() {
        return unusedCount;
    }

    public void setUnusedCount(long unusedCount) {
        this.unusedCount = unusedCount;
    }

    public long getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(long usedCount) {
        this.usedCount = usedCount;
    }

    public long getOverdueCount() {
        return overdueCount;
    }

    public void setOverdueCount(long overdueCount) {
        this.overdueCount = overdueCount;
    }

    public List<CouponDto> getUnusedCoupons() {
        return unusedCoupons;
    }

    public void setUnusedCoupons(List<CouponDto> unusedCoupons) {
        this.unusedCoupons = unusedCoupons;
    }

    public List<CouponDto> getUsedCoupons() {
        return usedCoupons;
    }

    public void setUsedCoupons(List<CouponDto> usedCoupons) {
        this.usedCoupons = usedCoupons;
    }

    public List<CouponDto> getOverdueCoupons() {
        return overdueCoupons;
    }

    public void setOverdueCoupons(List<CouponDto> overdueCoupons) {
        this.overdueCoupons = overdueCoupons;
    }
}
