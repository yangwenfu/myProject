package com.xinyunlian.jinfu.bank.dto;

import java.io.Serializable;

/**
 * 银行Entity
 * @author KimLL
 * @version 
 */

public class BankInfDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long bankId;

	//联行号
	private String bankCnapsCode;

	//银行名称
	private String bankName;

	//银行代码
	private String	bankCode;

	private String bankLogo;
	
	private boolean support;

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBankCnapsCode() {
		return bankCnapsCode;
	}

	public void setBankCnapsCode(String bankCnapsCode) {
		this.bankCnapsCode = bankCnapsCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankLogo() {
		return bankLogo;
	}

	public void setBankLogo(String bankLogo) {
		this.bankLogo = bankLogo;
	}

	public boolean isSupport() {
		return support;
	}

	public void setSupport(boolean support) {
		this.support = support;
	}
}


