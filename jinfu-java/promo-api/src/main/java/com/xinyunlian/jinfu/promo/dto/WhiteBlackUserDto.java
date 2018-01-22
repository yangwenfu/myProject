package com.xinyunlian.jinfu.promo.dto;

import com.xinyunlian.jinfu.common.dto.BaseMaintainableDto;
import com.xinyunlian.jinfu.promo.enums.ERecordType;

/**
 * 黑白名单
 * @author jll
 * @version 
 */

public class WhiteBlackUserDto extends BaseMaintainableDto {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long promoId;
	private String userName;
	private String mobile;
	private String idCardNo;
	private String tobaccoCertificateNo;
	private ERecordType recordType;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getTobaccoCertificateNo() {
		return tobaccoCertificateNo;
	}

	public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
		this.tobaccoCertificateNo = tobaccoCertificateNo;
	}

	public ERecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(ERecordType recordType) {
		this.recordType = recordType;
	}
}


