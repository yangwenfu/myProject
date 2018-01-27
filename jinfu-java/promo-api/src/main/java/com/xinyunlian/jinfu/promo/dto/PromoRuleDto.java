package com.xinyunlian.jinfu.promo.dto;

import com.xinyunlian.jinfu.promo.enums.EProperty;
import com.xinyunlian.jinfu.rule.enums.EOffType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 促销规则信息
 * @author jll
 * @version 
 */

public class PromoRuleDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long promoId;
	private EProperty property;
	//满减类型:按金额、利率
	private EOffType offType;
	//按金额则为折扣、按利率则为满减金额
	private BigDecimal discount;
	//期数 等额-1为整期1为首期 随见随还 为天
	private Integer term;

	public Long getPromoId() {
		return promoId;
	}

	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}

	public EProperty getProperty() {
		return property;
	}

	public void setProperty(EProperty property) {
		this.property = property;
	}

	public EOffType getOffType() {
		return offType;
	}

	public void setOffType(EOffType offType) {
		this.offType = offType;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}
}


