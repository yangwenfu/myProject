package com.xinyunlian.jinfu.promo.dto;

import java.io.Serializable;

/**
 * 促销地区
 * @author jll
 * @version 
 */

public class PromoAreaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long promoId;
	private Long areaId;
	private String areaTreePath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPromoId() {
		return promoId;
	}

	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaTreePath() {
		return areaTreePath;
	}

	public void setAreaTreePath(String areaTreePath) {
		this.areaTreePath = areaTreePath;
	}
}


