package com.xinyunlian.jinfu.yunma.dto;

import com.xinyunlian.jinfu.trade.enums.EBizCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 云码店铺费率配置Entity
 * @author jll
 * @version 
 */

public class YmMemberBizDto implements Serializable {
	private static final long serialVersionUID = 1L;
	//商户业务配置表
	private Long id;
	//商户号
	private String memberNo;
	//业务编码
	private EBizCode bizCode;
	//业务扣率
	private BigDecimal rate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public EBizCode getBizCode() {
		return bizCode;
	}

	public void setBizCode(EBizCode bizCode) {
		this.bizCode = bizCode;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

}


