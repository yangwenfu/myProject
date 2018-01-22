package com.xinyunlian.jinfu.promo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 个人优惠券Entity
 *
 * @author jll
 */

public class CouponDto implements Serializable {
    private static final long serialVersionUID = 1L;
    //
    private Long id;
    //用户id
    private String userId;
    //
    private Long promoId;
    //优惠券名称
    private String couponName;
    //优惠类型
    private String couponType;
    //优惠券金额
    private String price;
    //是否叠加
    private String addedNote;
    //有效起始日期
    private Date validBeginDate;
    //有效结束日期
    private Date validEndDate;
    //产品ID
    private String prodId;
    //产品名称
    private String prodNameNote;
    //最小满足金额
    private String minimumNote;

    private boolean beOverdue;

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

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddedNote() {
        return addedNote;
    }

    public void setAddedNote(String addedNote) {
        this.addedNote = addedNote;
    }

    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
    public Date getValidBeginDate() {
        return validBeginDate;
    }

    public void setValidBeginDate(Date validBeginDate) {
        this.validBeginDate = validBeginDate;
    }

    @JsonFormat(pattern = "yyyy.MM.dd",timezone="GMT+8")
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

    public String getProdNameNote() {
        return prodNameNote;
    }

    public void setProdNameNote(String prodNameNote) {
        this.prodNameNote = prodNameNote;
    }

    public String getMinimumNote() {
        return minimumNote;
    }

    public void setMinimumNote(String minimumNote) {
        this.minimumNote = minimumNote;
    }

    public boolean isBeOverdue() {
        return beOverdue;
    }

    public void setBeOverdue(boolean beOverdue) {
        this.beOverdue = beOverdue;
    }
}


