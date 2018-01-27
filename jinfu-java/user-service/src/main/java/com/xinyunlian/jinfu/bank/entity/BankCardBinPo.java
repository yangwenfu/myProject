package com.xinyunlian.jinfu.bank.entity;

import com.xinyunlian.jinfu.common.entity.BasePo;

import javax.persistence.*;

/**
 * 卡BinEntity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "BANK_CARD_BIN")
public class BankCardBinPo extends BasePo{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;

	@Column(name="BANK_ID")
	private Long bankId;

	@Column(name="BANK_CODE")
	private String bankCode;

	@Column(name="BANK_NAME")
	private String bankName;

	@Column(name="CARD_NAME")
	private String cardName;

	@Column(name="CARD_TYPE")
	private String cardType;

	@Column(name="NUM_LENGTH")
	private int numLength;

	@Column(name="BIN_LENGTH")
	private int binLength;

	@Column(name="BIN")
	private String bin;
	public void setId(long id){
		this.id=id;
	}
	public long getId(){
		return id;
	}
	public void setBankCode(String bankCode){
		this.bankCode=bankCode;
	}
	public String getBankCode(){
		return bankCode;
	}
	public void setBankName(String bankName){
		this.bankName=bankName;
	}
	public String getBankName(){
		return bankName;
	}
	public void setCardName(String cardName){
		this.cardName=cardName;
	}
	public String getCardName(){
		return cardName;
	}
	public void setCardType(String cardType){
		this.cardType=cardType;
	}
	public String getCardType(){
		return cardType;
	}
	public void setNumLength(int numLength){
		this.numLength=numLength;
	}
	public int getNumLength(){
		return numLength;
	}
	public void setBinLength(int binLength){
		this.binLength=binLength;
	}
	public int getBinLength(){
		return binLength;
	}
	public void setBin(String bin){
		this.bin=bin;
	}
	public String getBin(){
		return bin;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
}


