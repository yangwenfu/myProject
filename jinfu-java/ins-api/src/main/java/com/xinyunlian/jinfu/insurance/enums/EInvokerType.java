package com.xinyunlian.jinfu.insurance.enums;

/**
 * Created by DongFC on 2016-09-21.
 */
public enum EInvokerType {

    APP("APP", "移动端"), WEB("WEB","桌面WEB端");

    private String code;
    private String text;

    EInvokerType() {
    }

    EInvokerType(String code, String text) {
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
