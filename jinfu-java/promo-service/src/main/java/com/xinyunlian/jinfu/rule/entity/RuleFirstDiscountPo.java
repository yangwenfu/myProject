package com.xinyunlian.jinfu.rule.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.rule.enums.EDiscountType;
import com.xinyunlian.jinfu.rule.enums.converter.EDiscountTypeConverter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 首单折扣Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "RULE_FIRST_DISCOUNT")
public class RuleFirstDiscountPo extends BaseMaintainablePo {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="PROMO_ID")
	private Long promoId;

	@Convert(converter = EDiscountTypeConverter.class)
	@Column(name="DISCOUNT_TYPE")
	private EDiscountType discountType;

	@Column(name="DISCOUNT")
	private BigDecimal discount;

	@Column(name="TERM")
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


