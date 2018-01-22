package com.xinyunlian.jinfu.acct.enums;

public enum ETrxType  {

	NORMAL_TRAD("01","正常交易"),
	REVERSAL_TRAN("02","冲正交易"),
	BYREVERSAL_TRAN("03","被冲正交易"),
	ALL("ALL","全部");
	
	private String code;

    private String text;
	
	ETrxType(String code, String text){
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
