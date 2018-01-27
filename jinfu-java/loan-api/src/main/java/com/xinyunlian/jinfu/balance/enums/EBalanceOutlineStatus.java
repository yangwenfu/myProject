package com.xinyunlian.jinfu.balance.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum EBalanceOutlineStatus implements PageEnum{

	NOT("0","未对账"),
	ALREADY("1","已对账");

	private String code;

    private String text;

	EBalanceOutlineStatus(String code, String text){
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
