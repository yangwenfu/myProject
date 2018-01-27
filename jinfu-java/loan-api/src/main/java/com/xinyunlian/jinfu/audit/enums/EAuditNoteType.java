package com.xinyunlian.jinfu.audit.enums;

public enum EAuditNoteType {

	ALL("00", "全部"),
	PHONE("01","电话"),
	OTHERS("02","其他");

	private String code;

    private String text;

	EAuditNoteType(String code, String text){
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
