package com.xinyunlian.jinfu.channel.dto;

import java.io.Serializable;

/**
 * 渠道人员负责地区Entity
 * @author jll
 * @version 
 */

public class ChannelUserAreaDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String userId;
	//地区最子级id
	private Long areaId;
	//地区路径
	private String areaTreePath;

	private Long provinceId;

	private Long cityId;

	private String province;

	private String city;

	private String area;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaTreePath() {
		return areaTreePath;
	}

	public void setAreaTreePath(String areaTreePath) {
		this.areaTreePath = areaTreePath;
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
}


