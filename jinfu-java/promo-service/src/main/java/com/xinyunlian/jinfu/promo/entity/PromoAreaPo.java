package com.xinyunlian.jinfu.promo.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * 促销地区Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "PROMO_AREA")
public class PromoAreaPo extends BaseMaintainablePo {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="PROMO_ID")
	private Long promoId;

	@Column(name="AREA_ID")
	private Long areaId;

	@Column(name="AREA_TREE_PATH")
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


