package com.xinyunlian.jinfu.yunma.enums;

/**
 * Created by menglei on 2017年7月19日.
 */
public enum EYmPayChannel {

    ZHONGMA("ZHONGMA","众码付"),
    PINGAN("PINGAN","平安银行"),
    SMARTPAY("SMARTPAY","支付通");

    private String code;
    private String text;

    EYmPayChannel() {
    }

    EYmPayChannel(String code, String text) {
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
