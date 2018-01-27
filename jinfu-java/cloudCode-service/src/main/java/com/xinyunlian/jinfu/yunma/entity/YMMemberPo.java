package com.xinyunlian.jinfu.yunma.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.yunma.enums.EMemberAuditStatus;
import com.xinyunlian.jinfu.yunma.enums.EMemberIntoStatus;
import com.xinyunlian.jinfu.yunma.enums.EMemberStatus;
import com.xinyunlian.jinfu.yunma.enums.ESettlement;
import com.xinyunlian.jinfu.yunma.enums.converter.EMemberAuditStatusConverter;
import com.xinyunlian.jinfu.yunma.enums.converter.EMemberIntoStatusConverter;
import com.xinyunlian.jinfu.yunma.enums.converter.EMemberStatusConverter;
import com.xinyunlian.jinfu.yunma.enums.converter.ESettlementConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * 云码商铺Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "YM_MEMBER")
public class YMMemberPo extends BaseMaintainablePo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="STORE_ID")
	private Long storeId;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="MEMBER_NO")
	private String memberNo;

	@Column(name="QRCODE_NO")
	private String qrcodeNo;

	@Column(name="QRCODE_URL")
	private String qrcodeUrl;

	@Column(name="OPEN_ID")
	private String openId;

	@Column(name="SETTLEMENT")
	@Convert(converter = ESettlementConverter.class)
	private ESettlement settlement;

	@Column(name="BIND_TIME")
	private Date bindTime;

	@Column(name="BANK_CARD_ID")
	private Long bankCardId;

	@Column(name="CORP_BANK_ID")
	private Long corpBankId;

	@Column(name="STATUS")
	@Convert(converter = EMemberStatusConverter.class)
	private EMemberStatus memberStatus;

	@Column(name="AUDIT_STATUS")
	@Convert(converter = EMemberAuditStatusConverter.class)
	private EMemberAuditStatus memberAuditStatus;

	@Column(name="INTO_STATUS")
	@Convert(converter = EMemberIntoStatusConverter.class)
	private EMemberIntoStatus memberIntoStatus;

	@Column(name = "ACTIVATE")
	private Boolean activate;

	@Column(name="DEALER_USER_ID")
	private String dealerUserId;

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getQrcodeNo() {
		return qrcodeNo;
	}

	public void setQrcodeNo(String qrcodeNo) {
		this.qrcodeNo = qrcodeNo;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public ESettlement getSettlement() {
		return settlement;
	}

	public void setSettlement(ESettlement settlement) {
		this.settlement = settlement;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	public Long getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(Long bankCardId) {
		this.bankCardId = bankCardId;
	}

	public EMemberStatus getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(EMemberStatus memberStatus) {
		this.memberStatus = memberStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EMemberAuditStatus getMemberAuditStatus() {
		return memberAuditStatus;
	}

	public void setMemberAuditStatus(EMemberAuditStatus memberAuditStatus) {
		this.memberAuditStatus = memberAuditStatus;
	}

	public Boolean getActivate() {
		return activate;
	}

	public void setActivate(Boolean activate) {
		this.activate = activate;
	}

	public Long getCorpBankId() {
		return corpBankId;
	}

	public void setCorpBankId(Long corpBankId) {
		this.corpBankId = corpBankId;
	}

	public EMemberIntoStatus getMemberIntoStatus() {
		return memberIntoStatus;
	}

	public void setMemberIntoStatus(EMemberIntoStatus memberIntoStatus) {
		this.memberIntoStatus = memberIntoStatus;
	}

	public String getDealerUserId() {
		return dealerUserId;
	}

	public void setDealerUserId(String dealerUserId) {
		this.dealerUserId = dealerUserId;
	}
}


