package com.xinyunlian.jinfu.schedule.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum EScheduleStatus implements PageEnum{

	ALL("ALL","全部"),
	NOTYET("21", "使用中"),
	PAID("22","已还清"),
	OVERDUE("23","已逾期"),
	PROCESS("24", "处理中");
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
