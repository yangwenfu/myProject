package com.ylfin.wallet.portal.controller.vo;


import com.xinyunlian.jinfu.bank.enums.ECardType;
import com.xinyunlian.jinfu.bank.enums.ECardUsage;
import lombok.Data;

@Data
public class BankcardInfo {
    private long bankCardId;
    private String bankCardNo;
    private String bankName;
    private ECardType cardType;
    private ECardUsage usage;
    private String bankLogo;


}
