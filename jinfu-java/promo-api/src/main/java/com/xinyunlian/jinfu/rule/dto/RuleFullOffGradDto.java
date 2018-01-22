package com.xinyunlian.jinfu.rule.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 满减梯度Entity
 * @author jll
 * @version 
 */

public class RuleFullOffGradDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long offId;
	private BigDecimal amount;
	private BigDecimal discount;
	public void setId(Long id){
		this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setOffId(Long offId){
		this.offId=offId;
	}
	public Long getOffId(){
		return offId;
	}
	public void setAmount(BigDecimal amount){
		this.amount=amount;
	}
	public BigDecimal getAmount(){
		return amount;
	}
	public void setDiscount(BigDecimal discount){
		this.discount=discount;
	}
	public BigDecimal getDiscount(){
		return discount;
	}

}


