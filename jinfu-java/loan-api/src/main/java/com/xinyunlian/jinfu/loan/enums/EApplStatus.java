package com.xinyunlian.jinfu.loan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum EApplStatus implements PageEnum{

	ALL("ALL","全部"),
	TRIAL_UNCLAIMED("00","初审待领取"),
	TRIAL_CLAIMED("04", "初审已领取"),
	REVIEW_UNCLAIMED("01", "终审待领取"),
	REVIEW_CLAIMED("05", "终审已领取"),
	SUCCEED("02","通过"),
	REJECT("11", "拒绝"),
	FALLBACK("12", "回退"),
	CANCEL("13", "取消");

	private String code;

    private String text;
	
	EApplStatus(String code, String text){
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
