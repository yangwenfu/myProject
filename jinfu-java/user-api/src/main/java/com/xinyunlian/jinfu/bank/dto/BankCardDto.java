package com.xinyunlian.jinfu.bank.dto;

import com.xinyunlian.jinfu.bank.enums.ECardType;
import com.xinyunlian.jinfu.bank.enums.ECardUsage;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行卡Entity
 * @author KimLL
 * @version 
 */

public class BankCardDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private long bankCardId;
	private String userId;
	//银行卡号
	private String bankCardNo;
	//持卡人姓名
	private String bankCardName;
	//预留手机号
	private String mobileNo;
	//身份证ID
	private String idCardNo;
	//联行号
	private String bankCnapsCode;
	//发卡行名称
	private String bankName;
	//银行简写
	private String bankCode;

	//支行名称
	private String subbranchName;
	//支行号
	private String subbranchNo;
    //1-借记卡 2-信用卡
	private ECardType cardType;

	//卡片用处，01：支付卡 02：结算卡
	private ECardUsage usage;

	//银行id
	private Long bankId;

	private String verifyCode;

	private String bankLogo;

	private Date createTs;

	public void setBankCardId(long bankCardId){
		this.bankCardId=bankCardId;
	}
	public long getBankCardId(){
		return bankCardId;
	}
	public void setUserId(String userId){
		this.userId=userId;
	}
	public String getUserId(){
		return userId;
	}
	public void setBankCardNo(String bankCardNo){
		this.bankCardNo=bankCardNo;
	}
	public String getBankCardNo(){
		return bankCardNo;
	}
	public void setBankCardName(String bankCardName){
		this.bankCardName=bankCardName;
	}
	public String getBankCardName(){
		return bankCardName;
	}
	public void setMobileNo(String mobileNo){
		this.mobileNo=mobileNo;
	}
	public String getMobileNo(){
		return mobileNo;
	}
	public void setIdCardNo(String idCardNo){
		this.idCardNo=idCardNo;
	}
	public String getIdCardNo(){
		return idCardNo;
	}

	public String getBankCnapsCode() {
		return bankCnapsCode;
	}

	public void setBankCnapsCode(String bankCnapsCode) {
		this.bankCnapsCode = bankCnapsCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setBankName(String bankName){
		this.bankName=bankName;
	}
	public String getBankName(){
		return bankName;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getSubbranchName() {
		return subbranchName;
	}

	public void setSubbranchName(String subbranchName) {
		this.subbranchName = subbranchName;
	}

	public String getSubbranchNo() {
		return subbranchNo;
	}

	public void setSubbranchNo(String subbranchNo) {
		this.subbranchNo = subbranchNo;
	}

	public ECardType getCardType() {
		return cardType;
	}

	public void setCardType(ECardType cardType) {
		this.cardType = cardType;
	}

	public ECardUsage getUsage() {
		return usage;
	}

	public void setUsage(ECardUsage usage) {
		this.usage = usage;
	}

	public String getBankLogo() {
		return bankLogo;
	}

	public void setBankLogo(String bankLogo) {
		this.bankLogo = bankLogo;
	}

	public Date getCreateTs() {
		return createTs;
	}

	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}
}


