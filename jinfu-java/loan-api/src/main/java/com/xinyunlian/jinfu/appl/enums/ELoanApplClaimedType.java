package com.xinyunlian.jinfu.appl.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * 贷前管理-申请状态/终审分配状态
 */
public enum ELoanApplClaimedType implements PageEnum{

	ALL("ALL", "全部"),
	CLAIMED("CLAIMED","已分配"),
	UNCLAIMED("UNCLAIMED","待分配");

	private String code;

    private String text;

	ELoanApplClaimedType(String code, String text){
		this.code = code;
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
