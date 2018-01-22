package com.xinyunlian.jinfu.rule.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.rule.enums.EOffType;
import com.xinyunlian.jinfu.rule.enums.converter.EOffTypeConverter;

import javax.persistence.*;

/**
 * 满减Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "RULE_FULL_OFF")
public class RuleFullOffPo extends BaseMaintainablePo {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="OFF_ID")
	private Long offId;

	@Column(name="PROMO_ID")
	private Long promoId;

	@Column(name="CAP")
	private Boolean cap;

	@Convert(converter = EOffTypeConverter.class)
	@Column(name="OFF_TYPE")
	private EOffType offType;

	@Column(name="TERM")
	private Integer term;
	public void setOffId(Long offId){
		this.offId=offId;
	}
	public Long getOffId(){
		return offId;
	}
	public void setPromoId(Long promoId){
		this.promoId=promoId;
	}
	public Long getPromoId(){
		return promoId;
	}

	public Boolean getCap() {
		return cap;
	}

	public void setCap(Boolean cap) {
		this.cap = cap;
	}

	public EOffType getOffType() {
		return offType;
	}

	public void setOffType(EOffType offType) {
		this.offType = offType;
	}

	public void setTerm(Integer term){
		this.term=term;
	}
	public Integer getTerm(){
		return term;
	}


}


