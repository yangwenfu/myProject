package com.xinyunlian.jinfu.order.enums;

/**
 * Created by menglei on 2016年11月20日.
 */
public enum ECmccOrderPayStatus {

    SUCCESS("SUCCESS","成功"),NOPAYMENT("NOPAYMENT","未付款");

    private String code;
    private String text;

    ECmccOrderPayStatus() {
    }

    ECmccOrderPayStatus(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
