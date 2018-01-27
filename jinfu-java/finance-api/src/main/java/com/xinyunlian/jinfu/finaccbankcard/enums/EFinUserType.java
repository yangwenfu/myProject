package com.xinyunlian.jinfu.finaccbankcard.enums;

/**
 * Created by dongfangchao on 2016/11/28/0028.
 */
public enum EFinUserType {

    SUPER_CASH("SUPER_CASH","4");

    private String code;
    private String text;

    EFinUserType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    EFinUserType() {
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
