package com.xinyunlian.jinfu.promo.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.rule.enums.ECouponType;
import com.xinyunlian.jinfu.promo.enums.EUserCouponStatus;
import com.xinyunlian.jinfu.rule.enums.converter.ECouponTypeConverter;
import com.xinyunlian.jinfu.promo.enums.converter.EUserCouponStatusConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 个人优惠券Entity
 *
 * @author jll
 */
@Entity
@Table(name = "USER_COUPON")
public class UserCouponPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALID_BEGIN_DATE")
    private Date validBeginDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALID_END_DATE")
    private Date validEndDate;

    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "PROD_TYPE_ID")
    private Integer prodTypeId;

    @Column(name = "MINIMUM")
    private BigDecimal minimum;

    @Column(name = "STATUS")
    @Convert(converter = EUserCouponStatusConverter.class)
    private EUserCouponStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Integer getProdTypeId() {
        return prodTypeId;
    }

    public void setProdTypeId(Integer prodTypeId) {
        this.prodTypeId = prodTypeId;
    }

    public BigDecimal getMinimum() {
        return minimum;
    }

    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }

    public EUserCouponStatus getStatus() {
        return status;
    }

    public void setStatus(EUserCouponStatus status) {
        this.status = status;
    }
}


