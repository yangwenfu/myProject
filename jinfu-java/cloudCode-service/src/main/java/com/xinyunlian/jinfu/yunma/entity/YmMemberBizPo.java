package com.xinyunlian.jinfu.yunma.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.enums.converters.EBizCodeConverter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 云码店铺费率配置Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "YM_MEMBER_BIZ")
public class YmMemberBizPo extends BaseMaintainablePo {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="MEMBER_NO")
	private String memberNo;

	@Column(name="BIZ_CODE")
	@Convert(converter = EBizCodeConverter.class)
	private EBizCode bizCode;

	@Column(name="RATE")
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


