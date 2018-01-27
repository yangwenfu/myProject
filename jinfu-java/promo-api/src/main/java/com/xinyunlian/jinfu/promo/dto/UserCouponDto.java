package com.xinyunlian.jinfu.promo.dto;

import com.xinyunlian.jinfu.rule.enums.ECouponType;
import com.xinyunlian.jinfu.promo.enums.EUserCouponStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 个人优惠券Entity
 *
 * @author jll
 */

public class UserCouponDto implements Serializable {
    private static final long serialVersionUID = 1L;
    //
    private Long id;
    //用户id
    private String userId;
    //
    private Long promoId;
    //优惠券名称
    private String couponName;

    private String couponCode;
    //优惠类型
    private ECouponType couponType;
    //优惠券金额
    private BigDecimal price;
    //是否叠加
    private Boolean added;
    //有效起始日期
    private Date validBeginDate;
    //有效结束日期
    private Date validEndDate;
    //产品ID
    private String prodId;
    private Integer prodTypeId;
    //最小满足金额
    private BigDecimal minimum;
    //状态
    private EUserCouponStatus status;

    private boolean onBlack;

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

    public boolean isOnBlack() {
        return onBlack;
    }

    public void setOnBlack(boolean onBlack) {
        this.onBlack = onBlack;
    }

    @Override
    public String toString() {
        return "UserCouponDto{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", promoId=" + promoId +
                ", couponName='" + couponName + '\'' +
                ", couponType=" + couponType +
                ", price=" + price +
                ", added=" + added +
                ", validBeginDate=" + validBeginDate +
                ", validEndDate=" + validEndDate +
                ", prodId='" + prodId + '\'' +
                ", minimum=" + minimum +
                ", status=" + status +
                '}';
    }
}


