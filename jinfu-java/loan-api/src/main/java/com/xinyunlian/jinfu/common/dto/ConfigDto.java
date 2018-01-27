package com.xinyunlian.jinfu.common.dto;


public class ConfigDto extends BaseMaintainableDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cfgKey;

	private String cfgValue;

	private String memo;

	public String getCfgKey() {
		return cfgKey;
	}

	public void setCfgKey(String cfgKey) {
		this.cfgKey = cfgKey;
	}

	public String getCfgValue() {
		return cfgValue;
	}

	public void setCfgValue(String cfgValue) {
		this.cfgValue = cfgValue;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
