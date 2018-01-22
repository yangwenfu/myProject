package com.xinyunlian.jinfu.yunma.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * Created by menglei on 2017-08-31.
 */
public class MerchantScanDto {

    private static final long serialVersionUID = 506057658733074423L;

    //二维码
    @NotBlank(message = "二维码编号不能为空")
    private String qrCodeNo;
    //支付二维码
    @NotBlank(message = "支付二维码不能为空")
    private String payCode;
    //消费金额
    @NotBlank(message = "支付金额不能为空")
    private BigDecimal amount;

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
}
