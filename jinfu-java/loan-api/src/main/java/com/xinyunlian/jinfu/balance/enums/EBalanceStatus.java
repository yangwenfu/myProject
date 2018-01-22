package com.xinyunlian.jinfu.balance.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum EBalanceStatus implements PageEnum{

	NOT("0","未勾对"),
	ALREADY("1","已勾对");

	private String code;

    private String text;

	EBalanceStatus(String code, String text){
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
