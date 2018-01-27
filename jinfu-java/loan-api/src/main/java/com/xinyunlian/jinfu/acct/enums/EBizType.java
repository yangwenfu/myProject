package com.xinyunlian.jinfu.acct.enums;

public enum EBizType  {

	LOAN("01","贷款"),
	REPAYMENT("02","还款"),
	FEE("03","手续费"),
	INTEREST_INCOME("04","利息收入"),
	ALL("ALL","全部");
	
	private String code;

    private String text;
	
	EBizType(String code, String text){
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
