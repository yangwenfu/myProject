package com.xinyunlian.jinfu.shopkeeper.dto.my;

import com.xinyunlian.jinfu.bank.enums.ECardType;

import java.io.Serializable;

/**
 * Created by King on 2017/2/16.
 */
public class BankCardEachDto implements Serializable{
    private static final long serialVersionUID = 2918419453204256897L;
    private long bankCardId;
    //银行卡号
    private String bankCardNo;
    //发卡行名称
    private String bankName;
    //银行简写
    private String bankCode;
    //1-借记卡 2-信用卡
    private ECardType cardType;
    //银行id
    private Long bankId;

    private String bankLogo;

    public long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
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

    public ECardType getCardType() {
        return cardType;
    }

    public void setCardType(ECardType cardType) {
        this.cardType = cardType;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }
}
