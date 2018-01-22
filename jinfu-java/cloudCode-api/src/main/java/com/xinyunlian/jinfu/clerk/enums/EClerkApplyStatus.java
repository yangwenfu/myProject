package com.xinyunlian.jinfu.clerk.enums;

/**
 * Created by menglei on 2016年12月06日.
 */
public enum EClerkApplyStatus {

    SUCCESS("SUCCESS","成功"),FAILED("FAILED","失败"),APPLY("APPLY","申请中"),DELETE("DELETE","注销");

    private String code;
    private String text;

    EClerkApplyStatus() {
    }

    EClerkApplyStatus(String code, String text) {
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
