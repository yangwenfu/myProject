package com.xinyunlian.jinfu.appl.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * 贷前管理-初审  状态
 */
public enum ELoanApplTrialType implements PageEnum{

	ALL("ALL", "全部"),
	WAIT("WAIT","待初审"),
	ALREADY("ALREADY","已初审"),
	FALLBACK("FALLBACK", "已退回");

	private String code;

    private String text;

	ELoanApplTrialType(String code, String text){
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
