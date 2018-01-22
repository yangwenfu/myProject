package com.xinyunlian.jinfu.rule.dto;

import com.xinyunlian.jinfu.rule.enums.ECouponType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券Entity
 * @author jll
 * @version 
 */

public class RuleCouponDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	//
	private Long promoId;
	//优惠券名称
	private String couponName;
	//优惠券code
	private String couponCode;
	//优惠类型
	private ECouponType couponType;
	//优惠券金额
	private BigDecimal price;
	//是否叠加
	private Boolean added;
	//有效日
	private Integer validDays;
	//有效起始日期
	private Date validBeginDate;
	//有效结束日期
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


