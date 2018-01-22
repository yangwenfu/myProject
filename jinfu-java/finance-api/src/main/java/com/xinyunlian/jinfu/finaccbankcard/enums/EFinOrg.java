package com.xinyunlian.jinfu.finaccbankcard.enums;

/**
 * Created by dongfangchao on 2016/11/22.
 */
public enum EFinOrg {

    ZRFUNDS("ZRFUNDS","中融基金");

    private String code;
    private String text;

    EFinOrg(String code, String text) {
        this.code = code;
        this.text = text;
    }

    EFinOrg() {
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
