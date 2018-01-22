package com.xinyunlian.jinfu.rule.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 满减梯度Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "RULE_FULL_OFF_GRAD")
public class RuleFullOffGradPo extends BaseMaintainablePo {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="OFF_ID")
	private Long offId;

	@Column(name="AMOUNT")
	private BigDecimal amount;

	@Column(name="DISCOUNT")
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


