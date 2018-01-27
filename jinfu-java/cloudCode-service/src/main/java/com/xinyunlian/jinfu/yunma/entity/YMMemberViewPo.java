package com.xinyunlian.jinfu.yunma.entity;

import com.xinyunlian.jinfu.common.entity.BasePo;
import com.xinyunlian.jinfu.yunma.enums.EMemberStatus;
import com.xinyunlian.jinfu.yunma.enums.converter.EMemberStatusConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * 云码商铺视图Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "YM_MEMBER_VIEW")
public class YMMemberViewPo extends BasePo {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private Long id;

	@Column(name="STORE_ID")
	private Long storeId;

	@Column(name="USER_ID")
	private String userId;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name="MEMBER_NO")
	private String memberNo;

	@Column(name="QRCODE_NO")
	private String qrcodeNo;

	@Column(name="QRCODE_URL")
	private String qrcodeUrl;

	@Column(name="OPEN_ID")
	private String openId;

	@Column(name="SETTLEMENT")
	private String settlement;

	@Column(name="BIND_TIME")
	private Date bindTime;

	@Column(name="BANK_CARD_ID")
	private Long bankCardId;

	@Column(name="STATUS")
	@Convert(converter = EMemberStatusConverter.class)
	private EMemberStatus memberStatus;

	@Column(name = "STORE_NAME")
	private String storeName;

	@Column(name = "AREA_ID")
	private Long areaId;

	@Column(name = "PROVINCE_ID")
	private Long provinceId;

	@Column(name = "CITY_ID")
	private Long cityId;

	@Column(name = "PROVINCE")
	private String province;

	@Column(name = "CITY")
	private String city;

	@Column(name = "AREA")
	private String area;

	@Column(name = "DEALER_USER_ID")
	private String dealerUserId;

	@Column(name = "DEALER_USER_NAME")
	private String dealerUserName;

	@Column(name = "DEALER_ID")
	private String dealerId;


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

	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
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

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDealerUserId() {
		return dealerUserId;
	}

	public void setDealerUserId(String dealerUserId) {
		this.dealerUserId = dealerUserId;
	}

	public String getDealerUserName() {
		return dealerUserName;
	}

	public void setDealerUserName(String dealerUserName) {
		this.dealerUserName = dealerUserName;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
}


