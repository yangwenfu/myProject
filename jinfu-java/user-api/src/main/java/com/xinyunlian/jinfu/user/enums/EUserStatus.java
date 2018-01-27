package com.xinyunlian.jinfu.user.enums;

/**
 * Created by JL on 2016/8/19.
 */
public enum EUserStatus {

    NORMAL("NORMAL", "正常"),
    All("ALL", "全部");

    private String code;

    private String text;

    EUserStatus(String code, String text) {
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
