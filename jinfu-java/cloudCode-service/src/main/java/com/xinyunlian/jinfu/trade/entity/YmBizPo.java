package com.xinyunlian.jinfu.trade.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.enums.converters.EBizCodeConverter;
import com.xinyunlian.jinfu.yunma.enums.ESettlement;
import com.xinyunlian.jinfu.yunma.enums.converter.ESettlementConverter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 云码业务配置Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "YM_BIZ")
public class YmBizPo extends BaseMaintainablePo {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="NAME")
	private String name;

	@Column(name="CODE")
	@Convert(converter = EBizCodeConverter.class)
	private EBizCode code;

	@Column(name="RATE")
	private BigDecimal rate;

	@Column(name="SETTLEMENT")
	@Convert(converter = ESettlementConverter.class)
	private ESettlement settlement;

	@Column(name="STATUS")
	private Boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EBizCode getCode() {
		return code;
	}

	public void setCode(EBizCode code) {
		this.code = code;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public ESettlement getSettlement() {
		return settlement;
	}

	public void setSettlement(ESettlement settlement) {
		this.settlement = settlement;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}


