package com.xinyunlian.jinfu.rule.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.rule.enums.ECouponType;
import com.xinyunlian.jinfu.rule.enums.converter.ECouponTypeConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券Entity
 *
 * @author jll
 */
@Entity
@Table(name = "RULE_COUPON")
public class RuleCouponPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROMO_ID")
    private Long promoId;

    @Column(name = "COUPON_NAME")
    private String couponName;

    @Column(name = "COUPON_CODE")
    private String couponCode;

    @Column(name = "COUPON_TYPE")
    @Convert(converter = ECouponTypeConverter.class)
    private ECouponType couponType;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "IS_ADDED")
    private Boolean added;

    @Column(name = "VALID_DAYS")
    private Integer validDays;

    @Column(name = "VALID_BEGIN_DATE")
    private Date validBeginDate;

    @Column(name = "VALID_END_DATE")
    private Date validEndDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(Long promoId) {
        this.promoId = promoId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public ECouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(ECouponType couponType) {
        this.couponType = couponType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getAdded() {
        return added;
    }

    public void setAdded(Boolean added) {
        this.added = added;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public Date getValidBeginDate() {
        return validBeginDate;
    }

    public void setValidBeginDate(Date validBeginDate) {
        this.validBeginDate = validBeginDate;
    }

    public Date getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(Date validEndDate) {
        this.validEndDate = validEndDate;
    }
}


