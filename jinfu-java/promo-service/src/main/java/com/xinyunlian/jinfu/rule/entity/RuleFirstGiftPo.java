package com.xinyunlian.jinfu.rule.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 首单有礼Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "RULE_FIRST_GIFT")
public class RuleFirstGiftPo extends BaseMaintainablePo {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="PROMO_ID")
	private Long promoId;

	@Column(name="GIFT_NAME")
	private String giftName;

	@Column(name="[DESCRIBE]")
	private String describe;

	@Column(name="PRICE")
	private BigDecimal price;

	@Column(name="TOTAL")
	private Integer total;

	@Column(name="PER_NUM")
	private Integer perNum;
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
	public void setGiftName(String giftName){
		this.giftName=giftName;
	}
	public String getGiftName(){
		return giftName;
	}
	public void setDescribe(String describe){
		this.describe=describe;
	}
	public String getDescribe(){
		return describe;
	}
	public void setPrice(BigDecimal price){
		this.price=price;
	}
	public BigDecimal getPrice(){
		return price;
	}
	public void setTotal(Integer total){
		this.total=total;
	}
	public Integer getTotal(){
		return total;
	}
	public void setPerNum(Integer perNum){
		this.perNum=perNum;
	}
	public Integer getPerNum(){
		return perNum;
	}


}


