package com.xinyunlian.jinfu.bank.entity;

import com.xinyunlian.jinfu.bank.enums.EBankCardStatus;
import com.xinyunlian.jinfu.bank.enums.ECardType;
import com.xinyunlian.jinfu.bank.enums.ECardUsage;
import com.xinyunlian.jinfu.bank.enums.converter.EBankCardStatusConverter;
import com.xinyunlian.jinfu.bank.enums.converter.ECardTypeConverter;
import com.xinyunlian.jinfu.bank.enums.converter.ECardUsageConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.user.entity.UserInfoPo;

import javax.persistence.*;

/**
 * 银行卡Entity
 * @author KimLL
 * @version
 */
@Entity
@Table(name = "BANK_CARD")
public class BankCardPo extends BaseMaintainablePo {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BANK_CARD_ID")
	private long bankCardId;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="BANK_CARD_NO")
	private String bankCardNo;

	@Column(name="BANK_CARD_NAME")
	private String bankCardName;

	@Column(name="MOBILE_NO")
	private String mobileNo;

	@Column(name="ID_CARD_NO")
	private String idCardNo;

	@Column(name="BANK_CNAPS_CODE")
	private String bankCnapsCode;

	@Column(name="BANK_CODE")
	private String bankCode;

	@Column(name="BANK_NAME")
	private String bankName;

	@Column(name="BANK_TYPE_CODE")
	private String bankTypeCode;

	@Column(name="PROV_CODE")
	private String provCode;

	@Column(name="CITY_CODE")
	private String cityCode;

	@Column(name="SUBBRANCH_NAME")
	private String subbranchName;

	@Column(name="SUBBRANCH_NO")
	private String subbranchNo;

	@Column(name="CARD_TYPE")
	@Convert(converter = ECardTypeConverter.class)
	private ECardType cardType;

	@Column(name="[USAGE]")
	@Convert(converter = ECardUsageConverter.class)
	private ECardUsage usage;

	@Column(name = "STATUS")
	@Convert(converter = EBankCardStatusConverter.class)
	private EBankCardStatus status;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private UserInfoPo userInfoPo;

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
	public void setBankTypeCode(String bankTypeCode){
		this.bankTypeCode=bankTypeCode;
	}
	public String getBankTypeCode(){
		return bankTypeCode;
	}
	public void setProvCode(String provCode){
		this.provCode=provCode;
	}
	public String getProvCode(){
		return provCode;
	}
	public void setCityCode(String cityCode){
		this.cityCode=cityCode;
	}
	public String getCityCode(){
		return cityCode;
	}

	public UserInfoPo getUserInfoPo() {
		return userInfoPo;
	}

	public void setUserInfoPo(UserInfoPo userInfoPo) {
		this.userInfoPo = userInfoPo;
	}

	public EBankCardStatus getStatus() {
		return status;
	}

	public void setStatus(EBankCardStatus status) {
		this.status = status;
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
}


