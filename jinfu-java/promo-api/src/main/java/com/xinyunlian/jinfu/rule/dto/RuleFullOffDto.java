package com.xinyunlian.jinfu.rule.dto;

import com.xinyunlian.jinfu.rule.enums.EOffType;

import java.io.Serializable;
import java.util.List;

/**
 * 满减Entity
 * @author jll
 * @version 
 */

public class RuleFullOffDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long offId;
	private Long promoId;
	private Boolean cap;
	private EOffType offType;
	private Integer term;
	private List<RuleFullOffGradDto> ruleFullOffGradDtos;

	public Long getOffId() {
		return offId;
	}

	public void setOffId(Long offId) {
		this.offId = offId;
	}

	public Long getPromoId() {
		return promoId;
	}

	public void setPromoId(Long promoId) {
		this.promoId = promoId;
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

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public List<RuleFullOffGradDto> getRuleFullOffGradDtos() {
		return ruleFullOffGradDtos;
	}

	public void setRuleFullOffGradDtos(List<RuleFullOffGradDto> ruleFullOffGradDtos) {
		this.ruleFullOffGradDtos = ruleFullOffGradDtos;
	}
}


