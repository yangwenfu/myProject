package com.xinyunlian.jinfu.trade.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.enums.ETradeStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 云码流水表Entity
 * @author jll
 * @version 
 */

public class YmTradeSearchDto extends PagingDto<YmTradeDto> {
	private static final long serialVersionUID = 1L;
	//流水表
	private Long id;
	//流水号
	private String tradeNo;
	//通道流水
	private String outTradeNo;
	//商户号
	private String memberNo;
	//业务编码
	private EBizCode bizCode;
	//消费金额
	private BigDecimal transAmt;
	//消费手续费
	private BigDecimal transFee;
	//转入主卡号
	private String cardNo;
	//0未支付，1支付成功,2支付失败
	private ETradeStatus status;
	//创建时间
	private Date createTs;

	private String startCreateTs;

	private String endCreateTs;

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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public ETradeStatus getStatus() {
		return status;
	}

	public void setStatus(ETradeStatus status) {
		this.status = status;
	}

	public Date getCreateTs() {
		return createTs;
	}

	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}

	public String getStartCreateTs() {
		return startCreateTs;
	}

	public void setStartCreateTs(String startCreateTs) {
		this.startCreateTs = startCreateTs;
	}

	public String getEndCreateTs() {
		return endCreateTs;
	}

	public void setEndCreateTs(String endCreateTs) {
		this.endCreateTs = endCreateTs;
	}
}


