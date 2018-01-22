package com.xinyunlian.jinfu.yunma.enums;

/**
 * Created by menglei on 2017-01-09.
 */
public enum EUserRoleType {

    BOSS("boss", "店主"),
    CLERK("clerk", "店员");

    private String code;
    private String text;

    EUserRoleType(String code, String text) {
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
