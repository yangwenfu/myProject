package com.xinyunlian.jinfu.audit.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum EAuditStatus implements PageEnum{

	ALL("00","全部"),

	//状态,01-通过,11-拒绝,12-退回,13-取消,14-建议拒绝,15-建议通过

	SUCCEED("01", "通过"),
	REJECT("11", "拒绝"),
	FALLBACK("12", "退回"),
	CANCEL("13", "取消"),
	ADVISE_REJECT("14", "建议拒绝"),
	ADVISE_PASS("15", "建议通过");

	private String code;

    private String text;

	EAuditStatus(String code, String text){
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
