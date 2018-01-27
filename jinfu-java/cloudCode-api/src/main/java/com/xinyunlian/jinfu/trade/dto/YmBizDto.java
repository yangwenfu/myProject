package com.xinyunlian.jinfu.trade.dto;

import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.yunma.enums.ESettlement;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 云码业务配置Entity
 * @author jll
 * @version 
 */

public class YmBizDto implements Serializable {
	private static final long serialVersionUID = 1L;
	//主键
	private Long id;
	//业务名称
	private String name;
	//业务编码
	private EBizCode code;
	//默认通道扣率
	private BigDecimal rate;
	//结算方式
	private ESettlement settlement;
	//0正常，1废弃
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


