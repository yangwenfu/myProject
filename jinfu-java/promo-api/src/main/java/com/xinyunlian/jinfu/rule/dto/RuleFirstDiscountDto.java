package com.xinyunlian.jinfu.rule.dto;

import com.xinyunlian.jinfu.rule.enums.EDiscountType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 首单折扣Entity
 * @author jll
 * @version 
 */

public class RuleFirstDiscountDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long promoId;
	private EDiscountType discountType;
	private BigDecimal discount;
	private Integer term;
	public void setId(Long id){
		this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setPromoId(Long promoId){
		this.promoId=promoId;
	}
	public Long getPromoId(){
		return promoId;
	}

	public EDiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(EDiscountType discountType) {
		this.discountType = discountType;
	}

	public void setDiscount(BigDecimal discount){
		this.discount=discount;
	}
	public BigDecimal getDiscount(){
		return discount;
	}
	public void setTerm(Integer term){
		this.term=term;
	}
	public Integer getTerm(){
		return term;
	}

}


