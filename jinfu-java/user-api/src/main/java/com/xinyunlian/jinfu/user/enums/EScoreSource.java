package com.xinyunlian.jinfu.user.enums;

/**
 * Created by dongfangchao on 2017/5/19/0019.
 */
public enum EScoreSource {

    ANNIVERSARY("ANNIVERSARY","周年庆");

    private String code;

    private String text;

    EScoreSource(String code, String text) {
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
