package com.xinyunlian.jinfu.exam.enums;

/**
 * Created by menglei on 2017年05月02日.
 */
public enum EExamUserStatus {

    PASS("PASS", "及格"),
    FAIL("FAIL", "不及格");

    private String code;

    private String text;

    EExamUserStatus(String code, String text) {
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
