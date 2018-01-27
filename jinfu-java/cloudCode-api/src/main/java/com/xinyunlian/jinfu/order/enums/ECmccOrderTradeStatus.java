package com.xinyunlian.jinfu.order.enums;

/**
 * Created by menglei on 2016年11月20日.
 */
public enum ECmccOrderTradeStatus {

    SUCCESS("SUCCESS","打款成功"),
    NOPAYMENT("NOPAYMENT","未打款"),
    PROCESS("PROCESS","打款中"),
    FAILED("FAILED","打款失败");

    private String code;
    private String text;

    ECmccOrderTradeStatus() {
    }

    ECmccOrderTradeStatus(String code, String text) {
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
