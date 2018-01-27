package com.ylfin.wallet.provider.shangqi.req;

import lombok.Data;

@Data
public class PaymentReq {

    private String payType;

    private String goodsName;

    private String merchOrderNo;

    private String amount;

    private String phoneNo;

    private String customerName;

    private String cerdType;

    private String cerdId;

    private String cardNo;

    private String cvn2;

    private String validDate;
}
