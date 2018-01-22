package com.xinyunlian.jinfu.promo.dto;

import com.xinyunlian.jinfu.promo.enums.EPlatform;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;
import com.xinyunlian.jinfu.promo.enums.EPromoInfType;
import com.xinyunlian.jinfu.promo.enums.EProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 促销活动信息
 * @author jll
 * @version 
 */

public class PromoInfDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long promoId;
	private Long prodTypeId;
	private String prodTypeName;
	private String prodId;
	private String prodName;
	private EPromoInfType type;
	private String alias;
	private String name;
	private String describe;
	private List<EPlatform> platform = new ArrayList<>();
	private Date startDate;
	private Date endDate;
	private EProperty property;
	private EPromoInfStatus status;
	private BigDecimal minimum;
	private Integer totalLimit;
	private Integer perLimit;

	public Long getPromoId() {
		return promoId;
	}

	public void setPromoId(Long promoId) {
		this.promoId = promoId;
	}

	public Long getProdTypeId() {
		return prodTypeId;
	}

	public void setProdTypeId(Long prodTypeId) {
		this.prodTypeId = prodTypeId;
	}

	public String getProdTypeName() {
		return prodTypeName;
	}

	public void setProdTypeName(String prodTypeName) {
		this.prodTypeName = prodTypeName;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public EPromoInfType getType() {
		return type;
	}

	public void setType(EPromoInfType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public List<EPlatform> getPlatform() {
		return platform;
	}

	public void setPlatform(List<EPlatform> platform) {
		this.platform = platform;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public EProperty getProperty() {
		return property;
	}

	public void setProperty(EProperty property) {
		this.property = property;
	}

	public EPromoInfStatus getStatus() {
		return status;
	}

	public void setStatus(EPromoInfStatus status) {
		this.status = status;
	}

	public BigDecimal getMinimum() {
		return minimum;
	}

	public void setMinimum(BigDecimal minimum) {
		this.minimum = minimum;
	}

	public Integer getTotalLimit() {
		return totalLimit;
	}

	public void setTotalLimit(Integer totalLimit) {
		this.totalLimit = totalLimit;
	}

	public Integer getPerLimit() {
		return perLimit;
	}

	public void setPerLimit(Integer perLimit) {
		this.perLimit = perLimit;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}


