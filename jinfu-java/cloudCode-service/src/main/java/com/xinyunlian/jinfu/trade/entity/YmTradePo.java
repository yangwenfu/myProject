package com.xinyunlian.jinfu.trade.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.enums.ETradeStatus;
import com.xinyunlian.jinfu.trade.enums.ETradeType;
import com.xinyunlian.jinfu.trade.enums.converters.EBizCodeConverter;
import com.xinyunlian.jinfu.trade.enums.converters.ETradeStatusConverter;
import com.xinyunlian.jinfu.trade.enums.converters.ETradeTypeConverter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 云码流水表Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "YM_TRADE")
public class YmTradePo extends BaseMaintainablePo {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="TRADE_NO")
	private String tradeNo;

	@Column(name="OUT_TRADE_NO")
	private String outTradeNo;

	@Column(name="MEMBER_NO")
	private String memberNo;

	@Column(name="BIZ_CODE")
	@Convert(converter = EBizCodeConverter.class)
	private EBizCode bizCode;

	@Column(name="TRANS_AMT")
	private BigDecimal transAmt;

	@Column(name="TRANS_FEE")
	private BigDecimal transFee;

	@Column(name="TRANS_TIME")
	private String transTime;

	@Column(name="RESP_CODE")
	private String respCode;

	@Column(name="RESP_INFO")
	private String respInfo;

	@Column(name="CARD_NO")
	private String cardNo;

	@Column(name="OPENID")
	private String openid;

	@Column(name="STATUS")
	@Convert(converter = ETradeStatusConverter.class)
	private ETradeStatus status;

	@Column(name="TYPE")
	@Convert(converter = ETradeTypeConverter.class)
	private ETradeType type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
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

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

	public BigDecimal getTransFee() {
		return transFee;
	}

	public void setTransFee(BigDecimal transFee) {
		this.transFee = transFee;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespInfo() {
		return respInfo;
	}

	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public ETradeStatus getStatus() {
		return status;
	}

	public void setStatus(ETradeStatus status) {
		this.status = status;
	}

	public ETradeType getType() {
		return type;
	}

	public void setType(ETradeType type) {
		this.type = type;
	}
}


