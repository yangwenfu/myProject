package com.xinyunlian.jinfu.coupon.entity;

import com.xinyunlian.jinfu.common.entity.BasePo;
import com.xinyunlian.jinfu.promo.enmus.EPromoType;
import com.xinyunlian.jinfu.promo.enums.converter.EPromoTypeConverter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by JL on 2016/11/23.
 */
@Entity
@Table(name = "fp_loan_coupon")
public class LoanCouponPo extends BasePo{

    @Id
    @Column(name = "COUPON_ID")
    private Long couponId;

    @Column(name = "COUPON_CODE")
    private String couponCode;

    @Column(name ="REPAY_ID")
    private String repayId;

    @Column(name = "LOAN_ID")
    private String loanId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "COUPON_NAME")
    private String couponName;

    @Column(name = "COUPON_TYPE")
    private String couponType;

    @Column(name = "PRICE")
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
}
