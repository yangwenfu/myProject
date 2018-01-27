package com.xinyunlian.jinfu.dealer.enums;

/**
 * Created by menglei on 2016年08月26日.
 */
public enum EDealerType {

    SUBSIDIARY("SUBSIDIARY", "子公司 "),
    DEALER("DEALER", "分销商");

    private String code;

    private String text;

    EDealerType(String code, String text) {
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
