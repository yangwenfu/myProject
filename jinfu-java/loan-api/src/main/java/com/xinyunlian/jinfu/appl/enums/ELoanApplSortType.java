package com.xinyunlian.jinfu.appl.enums;

public enum ELoanApplSortType {

	ASC("ASC","顺序"),
	DESC("DESC","降序");

	private String code;

    private String text;

	ELoanApplSortType(String code, String text){
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
