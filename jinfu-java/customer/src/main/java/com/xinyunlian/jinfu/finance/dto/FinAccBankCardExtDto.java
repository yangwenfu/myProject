package com.xinyunlian.jinfu.finance.dto;

import com.xinyunlian.jinfu.bank.enums.ECardType;
import com.xinyunlian.jinfu.finaccbankcard.dto.FinAccBankCardDto;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public class FinAccBankCardExtDto extends FinAccBankCardDto {

    private static final long serialVersionUID = -4293893127011318260L;

    private ECardType cardType;

    public ECardType getCardType() {
        return cardType;
    }

    public void setCardType(ECardType cardType) {
        this.cardType = cardType;
    }
}
