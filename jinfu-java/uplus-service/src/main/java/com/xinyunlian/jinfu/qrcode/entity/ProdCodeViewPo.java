package com.xinyunlian.jinfu.qrcode.entity;

import com.xinyunlian.jinfu.qrcode.enums.EProdCodeStatus;
import com.xinyunlian.jinfu.qrcode.enums.converter.EProdCodeStatusConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品码视图Entity
 * @author menglei
 * @version 
 */
@Entity
@Table(name = "prod_code_view")
public class ProdCodeViewPo {

	@Id
	@Column(name="PROD_CODE_ID")
	private Long prodCodeId;

	@Column(name = "ORDER_PROD_ID")
	private Long orderProdId;

	@Column(name = "PROD_ID")
	private Long prodId;

	@Column(name = "STORE_ID")
	private String storeId;

	@Column(name = "PROD_ORDER_NO")
	private String prodOrderNo;

	@Column(name = "QRCODE_NO")
	private String qrCodeNo;

	@Column(name = "QRCODE_URL")
	private String qrCodeUrl;

	@Column(name = "BIND_TIME")
	private Date bindTime;

	@Column(name = "SELL_TIME")
	private Date sellTime;

	@Column(name = "FROZEN")
	private Boolean frozen;

	@Column(name = "STATUS")
	@Convert(converter = EProdCodeStatusConverter.class)
	private EProdCodeStatus status;

	@Column(name="CREATE_TS")
	private Date createTs;

	@Column(name="PROD_NAME")
	private String prodName;

	@Column(name="SKU")
	private String sku;

	@Column(name="STORE_NAME")
	private String storeName;

	@Column(name="PROVINCE_ID")
	private Long provinceId;

	@Column(name="CITY_ID")
	private Long cityId;

	@Column(name="AREA_ID")
	private Long areaId;

	@Column(name="PROVINCE")
	private String province;

	@Column(name="CITY")
	private String city;

	@Column(name="AREA")
	private String area;

	@Column(name="STREET")
	private String street;

	@Column(name="ADDRESS")
	private String address;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="MOBILE")
	private String mobile;

	@Column(name="ORDER_TIME")
	private Date orderTime;

	@Column(name="PLATFORM")
	private String platform;

	@Column(name="SUPPLIER")
	private String supplier;

	@Column(name="ORDER_NO")
	private String orderNo;

	@Column(name="STORAGE_MODE")
	private String storageMode;

	@Column(name="STORAGE_TIME")
	private String storageTime;

	public Long getProdCodeId() {
		return prodCodeId;
	}

	public void setProdCodeId(Long prodCodeId) {
		this.prodCodeId = prodCodeId;
	}

	public Long getOrderProdId() {
		return orderProdId;
	}

	public void setOrderProdId(Long orderProdId) {
		this.orderProdId = orderProdId;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getProdOrderNo() {
		return prodOrderNo;
	}

	public void setProdOrderNo(String prodOrderNo) {
		this.prodOrderNo = prodOrderNo;
	}

	public String getQrCodeNo() {
		return qrCodeNo;
	}

	public void setQrCodeNo(String qrCodeNo) {
		this.qrCodeNo = qrCodeNo;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	public Date getSellTime() {
		return sellTime;
	}

	public void setSellTime(Date sellTime) {
		this.sellTime = sellTime;
	}

	public Boolean getFrozen() {
		return frozen;
	}

	public void setFrozen(Boolean frozen) {
		this.frozen = frozen;
	}

	public EProdCodeStatus getStatus() {
		return status;
	}

	public void setStatus(EProdCodeStatus status) {
		this.status = status;
	}

	public Date getCreateTs() {
		return createTs;
	}

	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
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

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStorageMode() {
		return storageMode;
	}

	public void setStorageMode(String storageMode) {
		this.storageMode = storageMode;
	}

	public String getStorageTime() {
		return storageTime;
	}

	public void setStorageTime(String storageTime) {
		this.storageTime = storageTime;
	}
}


