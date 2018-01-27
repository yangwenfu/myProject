package com.xinyunlian.jinfu.report.virtual.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.report.dealer.enums.ESource;
import com.xinyunlian.jinfu.report.virtual.enums.ETakeType;

import java.util.Date;

/**
 * @author jll
 * @version 
 */

public class VirtualTboSearchDto extends PagingDto<VirtualTboSearchDto> {
	private String tobaccoCertificateNo;
	private String province;
	private String city;
	private String area;
	private String street;
	private ETakeType takeType;
	private String takeStartTime;
	private String takeEndTime;
	private Date takeTime;
	private String assignPerson;
	private String mobile;
	private String userName;
	private String remark;
	private String userId;
	private ESource source;

	public String getTobaccoCertificateNo() {
		return tobaccoCertificateNo;
	}

	public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
		this.tobaccoCertificateNo = tobaccoCertificateNo;
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

	public ETakeType getTakeType() {
		return takeType;
	}

	public void setTakeType(ETakeType takeType) {
		this.takeType = takeType;
	}

	public String getTakeStartTime() {
		return takeStartTime;
	}

	public void setTakeStartTime(String takeStartTime) {
		this.takeStartTime = takeStartTime;
	}

	public String getTakeEndTime() {
		return takeEndTime;
	}

	public void setTakeEndTime(String takeEndTime) {
		this.takeEndTime = takeEndTime;
	}

	public String getAssignPerson() {
		return assignPerson;
	}

	public void setAssignPerson(String assignPerson) {
		this.assignPerson = assignPerson;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Date takeTime) {
		this.takeTime = takeTime;
	}

	public ESource getSource() {
		return source;
	}

	public void setSource(ESource source) {
		this.source = source;
	}
}


