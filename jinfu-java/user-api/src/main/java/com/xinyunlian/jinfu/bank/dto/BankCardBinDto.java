package com.xinyunlian.jinfu.bank.dto;

import com.xinyunlian.jinfu.common.dto.BaseMaintainableDto;

/**
 * 卡BinEntity
 *
 * @author jll
 */

public class BankCardBinDto extends BaseMaintainableDto {

    private static final long serialVersionUID = 1L;

    private long id;
    private String bankCode;
    private String bankName;
    private String cardName;
    private String cardType;
    private int numLength;
    private int binLength;
    private String bin;
    private Long bankId;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setNumLength(int numLength) {
        this.numLength = numLength;
    }

    public int getNumLength() {
        return numLength;
    }

    public void setBinLength(int binLength) {
        this.binLength = binLength;
    }

    public int getBinLength() {
        return binLength;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getBin() {
        return bin;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }
}


