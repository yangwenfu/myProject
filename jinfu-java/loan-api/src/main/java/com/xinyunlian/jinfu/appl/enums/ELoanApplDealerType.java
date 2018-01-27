package com.xinyunlian.jinfu.appl.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum ELoanApplDealerType implements PageEnum{

	ALL("ALL", "全部"),
	SELF("SELF","自助成交"),
	DEALER("DEALER","分销代办");

	private String code;

    private String text;

	ELoanApplDealerType(String code, String text){
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
