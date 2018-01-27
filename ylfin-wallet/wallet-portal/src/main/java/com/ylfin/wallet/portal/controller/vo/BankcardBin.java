package com.ylfin.wallet.portal.controller.vo;

import com.xinyunlian.jinfu.bank.enums.ECardType;
import lombok.Data;

@Data
public class BankcardBin {
    private String bankCode;
    private String bankName;
    private String cardName;
    private ECardType cardType;
    private String bankCnapsCode;
    private Long bankId;
    private String bankLogo;

}
