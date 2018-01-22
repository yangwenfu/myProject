package com.xinyunlian.jinfu.appl.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * 贷前管理-终审  状态
 */
public enum ELoanApplReviewType implements PageEnum{

	ALL("ALL", "全部"),
	UNCLAIMED("UNCLAIMED", "待领取"),
	WAIT("WAIT","待终审"),
	ALREADY("ALREADY","已终审");

	private String code;

    private String text;

	ELoanApplReviewType(String code, String text){
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
