package com.xinyunlian.jinfu.audit.enums;

public enum EAuditType {

	TRIAL("01","初审"),
	REVIEW("02","终审");

	private String code;

    private String text;

	EAuditType(String code, String text){
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
