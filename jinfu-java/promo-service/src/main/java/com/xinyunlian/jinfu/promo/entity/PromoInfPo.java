package com.xinyunlian.jinfu.promo.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;
import com.xinyunlian.jinfu.promo.enums.EPromoInfType;
import com.xinyunlian.jinfu.promo.enums.EProperty;
import com.xinyunlian.jinfu.promo.enums.converter.EPromoInfStartusConverter;
import com.xinyunlian.jinfu.promo.enums.converter.EPromoInfTypeConverter;
import com.xinyunlian.jinfu.promo.enums.converter.EPropertyConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 促销活动信息Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "PROMO_INF")
public class PromoInfPo extends BaseMaintainablePo {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PROMO_ID")
	private Long promoId;

	@Column(name="PROD_TYPE_ID")
	private Long prodTypeId;

	@Column(name="PROD_ID")
	private String prodId;

	@Column(name="TYPE")
	@Convert(converter = EPromoInfTypeConverter.class)
	private EPromoInfType type;

	@Column(name="ALIAS")
	private String alias;

	@Column(name="NAME")
	private String name;

	@Column(name="[DESCRIBE]")
	private String describe;

	@Column(name="PLATFORM")
	private String platform;

	@Column(name="START_DATE")
	private Date startDate;

	@Column(name="END_DATE")
	private Date endDate;

	@Column(name="MINIMUM")
	private BigDecimal minimum;

	@Column(name="TOTAL_LIMIT")
	private Integer totalLimit;

	@Column(name="PER_LIMIT")
	private Integer perLimit;

	@Column(name="CUR_NUM")
	private Integer curNum = Integer.valueOf(0);

	@Column(name="property")
	@Convert(converter = EPropertyConverter.class)
	private EProperty property;

	@Column(name="STATUS")
	@Convert(converter = EPromoInfStartusConverter.class)
	private EPromoInfStatus status;

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

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
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

	public Integer getCurNum() {
		return curNum;
	}

	public void setCurNum(Integer curNum) {
		this.curNum = curNum;
	}

	public EProperty getProperty() {
		return property;
	}

	public void setProperty(EProperty property) {
		this.property = property;
	}
}


