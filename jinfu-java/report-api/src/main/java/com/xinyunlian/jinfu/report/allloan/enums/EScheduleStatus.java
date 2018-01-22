package com.xinyunlian.jinfu.report.allloan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum EScheduleStatus implements PageEnum {

	ALL("ALL","全部"),
	NOTYET("21", "未还款"),
	PAID("22","已还款"),
	OVERDUE("23","逾期");
	private String code;

    private String text;

	EScheduleStatus(String code, String text){
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
