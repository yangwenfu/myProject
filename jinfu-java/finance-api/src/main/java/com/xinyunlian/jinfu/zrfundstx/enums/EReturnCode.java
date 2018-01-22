package com.xinyunlian.jinfu.zrfundstx.enums;

/**
 * Created by dongfangchao on 2016/12/1/0001.
 */
public enum EReturnCode {

    SUCCESS("SUCCESS", "0000"), FAILURE("FAILURE", "9999");

    private String code;
    private String text;

    EReturnCode(String code, String text) {
        this.code = code;
        this.text = text;
    }

    EReturnCode() {
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
