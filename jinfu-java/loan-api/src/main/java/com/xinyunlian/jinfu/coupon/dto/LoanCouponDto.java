package com.xinyunlian.jinfu.coupon.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/3/27.
 */
public class LoanCouponDto implements Serializable{

    private String repayId;

    private String loanId;

    private String userId;

    private Long couponId;

    private String couponCode;

    private String couponName;

    private String couponType;

    private BigDecimal price;

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

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

    @Override
    public String toString() {
        return "LoanCouponDto{" +
                "repayId='" + repayId + '\'' +
                ", loanId='" + loanId + '\'' +
                ", userId='" + userId + '\'' +
                ", couponId=" + couponId +
                ", couponName='" + couponName + '\'' +
                ", couponType='" + couponType + '\'' +
                ", price=" + price +
                '}';
    }
}
