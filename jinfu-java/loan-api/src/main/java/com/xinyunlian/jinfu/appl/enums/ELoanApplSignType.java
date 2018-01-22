package com.xinyunlian.jinfu.appl.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * 贷中管理-签约  状态
 */
public enum ELoanApplSignType implements PageEnum{

	ALL("ALL", "全部"),
	WAIT("WAIT","待签约"),
	ALREADY("ALREADY","已签约");

	private String code;

    private String text;

	ELoanApplSignType(String code, String text){
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
