package com.xinyunlian.jinfu.trans.enums;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public enum EInsTransType {

    PINGAN_QUOTE_PRICE("PINGAN_QUOTE_PRICE", "平安报价查询"), PINGAN_QUNAR_APPLY("PINGAN_QUNAR_APPLY", "平安投保"), PINGAN_EPASELECPOLICY("PINGAN_EPASELECPOLICY", "平安电子保单获取");

    private String code;
    private String text;

    EInsTransType(String code, String text) {
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
