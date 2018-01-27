package com.xinyunlian.jinfu.virtual.dto;

import com.xinyunlian.jinfu.virtual.enums.ETakeStatus;
import com.xinyunlian.jinfu.virtual.enums.ETakeType;

import java.io.Serializable;
import java.util.Date;

/**
 * 虚拟烟草证Entity
 * @author jll
 * @version 
 */

public class VirtualTobaccoDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String tobaccoCertificateNo;
	private Long provinceId;
	private Long cityId;
	private Long areaId;
	private Long streetId;
	private String province;
	private String city;
	private String area;
	private String street;
	private String areaCode;
	private String pinCode;
	private Long serial;
	private String treePath;
	private ETakeType takeType;
	private ETakeStatus status;
	private Date takeTime;
	private String assignPerson;
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTobaccoCertificateNo() {
		return tobaccoCertificateNo;
	}

	public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
		this.tobaccoCertificateNo = tobaccoCertificateNo;
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

	public Long getStreetId() {
		return streetId;
	}

	public void setStreetId(Long streetId) {
		this.streetId = streetId;
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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Long getSerial() {
		return serial;
	}

	public void setSerial(Long serial) {
		this.serial = serial;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public ETakeType getTakeType() {
		return takeType;
	}

	public void setTakeType(ETakeType takeType) {
		this.takeType = takeType;
	}

	public Date getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Date takeTime) {
		this.takeTime = takeTime;
	}

	public String getAssignPerson() {
		return assignPerson;
	}

	public void setAssignPerson(String assignPerson) {
		this.assignPerson = assignPerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ETakeStatus getStatus() {
		return status;
	}

	public void setStatus(ETakeStatus status) {
		this.status = status;
	}
}


