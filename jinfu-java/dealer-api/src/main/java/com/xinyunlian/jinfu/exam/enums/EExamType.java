package com.xinyunlian.jinfu.exam.enums;

/**
 * Created by menglei on 2017年05月02日.
 */
public enum EExamType {

    FIRST("FIRST", "首考"),
    ROUTINE("ROUTINE", "常规");

    private String code;

    private String text;

    EExamType(String code, String text) {
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
