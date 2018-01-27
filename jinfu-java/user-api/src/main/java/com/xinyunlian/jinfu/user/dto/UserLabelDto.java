package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.user.enums.ELabelType;

import java.io.Serializable;

/**
 * 用户标签Entity
 * @author jll
 * @version 
 */

public class UserLabelDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//主键
	private Long id;
	//用户id
	private String userId;
	//标签类别
	private ELabelType labelType;

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

	public ELabelType getLabelType() {
		return labelType;
	}

	public void setLabelType(ELabelType labelType) {
		this.labelType = labelType;
	}
}


