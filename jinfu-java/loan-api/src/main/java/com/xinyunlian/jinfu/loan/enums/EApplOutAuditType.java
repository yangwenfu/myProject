package com.xinyunlian.jinfu.loan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum EApplOutAuditType implements PageEnum{

	ALL("ALL","全部"),
	SUCCESS("1", "同意"),
	REJECT("2", "拒绝");

	private String code;

    private String text;

	EApplOutAuditType(String code, String text){
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
