package com.xinyunlian.jinfu.promo.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.promo.enums.ERecordType;
import com.xinyunlian.jinfu.promo.enums.converter.ERecordTypeConverter;

import javax.persistence.*;

/**
 * 黑白名单Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "WHITE_BLACK_USER")
public class WhiteBlackUserPo extends BaseMaintainablePo {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="PROMO_ID")
	private Long promoId;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="MOBILE")
	private String mobile;

	@Column(name="ID_CARD_NO")
	private String idCardNo;

	@Column(name="TOBACCO_CERTIFICATE_NO")
	private String tobaccoCertificateNo;

	@Column(name = "record_type")
	@Convert(converter = ERecordTypeConverter.class)
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


