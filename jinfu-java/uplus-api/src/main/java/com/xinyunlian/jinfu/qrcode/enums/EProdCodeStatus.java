package com.xinyunlian.jinfu.qrcode.enums;

/**
 * Created by menglei on 2017年03月08日.
 */
public enum EProdCodeStatus {
    UNUSED("UNUSED", "未使用"),
    SALE("SALE", "未售"),
    SOLD("SOLD", "已售");

    private String code;

    private String text;

    EProdCodeStatus(String code, String text) {
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
