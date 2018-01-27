package com.xinyunlian.jinfu.rule.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 首单有礼Entity
 * @author jll
 * @version 
 */
public class RuleFirstGiftDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long promoId;
	private String giftName;
	private String describe;
	private BigDecimal price;
	private Integer total;
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


