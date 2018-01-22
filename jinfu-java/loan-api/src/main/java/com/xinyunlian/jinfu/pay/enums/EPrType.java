package com.xinyunlian.jinfu.pay.enums;

/**
 * 
 * @author cheng
 *
 */
public enum EPrType  {

	PAY("P","pay"),
	RECEIVE("R","receive"),
	//收银台还款
	CASHIER_RECEIVE("CR", "cashier_receive"),
	//存管还款
	DEPOSITOR_RECEIVE("DR", "depositor_receive"),
	//虚拟付款(假付款，只会产生指令，不会有真实交易)
	DUMMY_PAY("DP", "dummy_pay"),
	ALL("ALL","全部");
	
	private String code;

    private String text;
	
	EPrType(String code, String text){
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
