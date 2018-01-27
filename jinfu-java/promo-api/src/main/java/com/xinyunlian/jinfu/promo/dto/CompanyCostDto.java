package com.xinyunlian.jinfu.promo.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 主体信息
 * @author jll
 * @version 
 */

public class CompanyCostDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long costId;
	private Long promoId;
	private String companyName;
	private BigDecimal scale;

	public Long getCostId() {
		return costId;
	}

	public void setCostId(Long costId) {
		this.costId = costId;
	}

	public Long getPromoId() {
		return promoId;
	}

	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getScale() {
		return scale;
	}

	public void setScale(BigDecimal scale) {
		this.scale = scale;
	}
}


