package com.xinyunlian.jinfu.dealer.enums;

/**
 * Created by menglei on 2017年08月03日.
 */
public enum EDealerUserSubscribeType {

    YLJF("YLJF", "云联金服"),
    YLYM("YLYM", "云联云码");

    private String code;

    private String text;

    EDealerUserSubscribeType(String code, String text) {
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
