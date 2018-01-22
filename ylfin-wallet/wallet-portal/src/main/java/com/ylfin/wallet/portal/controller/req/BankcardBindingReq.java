package com.ylfin.wallet.portal.controller.req;

import com.xinyunlian.jinfu.bank.enums.ECardType;
import com.xinyunlian.jinfu.bank.enums.ECardUsage;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class BankcardBindingReq {
    private String userId;
    private String bankCardNo;
    private String bankCardName;
    private String mobileNo;
    private String idCardNo;
    private String bankCnapsCode;
    private String bankName;
    private String bankCode;
    private ECardType cardType;
    private Long bankId;
    private String verifyCode;
    private String bankLogo;
}
