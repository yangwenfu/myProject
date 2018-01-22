package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.bank.enums.ECardType;

import java.io.Serializable;

/**
 * 卡BinEntity
 *
 * @author jll
 */

public class CardBinOpenApi implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bankName;
    private String cardName;
    private ECardType cardType;
    private Long bankId;
    private String bankShortName;
    private String bankLogo;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
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

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }
}


