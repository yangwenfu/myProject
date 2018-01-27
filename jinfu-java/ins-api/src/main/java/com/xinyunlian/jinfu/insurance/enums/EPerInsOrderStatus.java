package com.xinyunlian.jinfu.insurance.enums;

/**
 * Created by DongFC on 2016-09-21.
 */
public enum EPerInsOrderStatus {

    SUCCESS("SUCCESS","成功"),FAILURE("FAILURE","失败"),INPROCESS("INPROCESS","处理中");

    private String code;
    private String text;

    EPerInsOrderStatus() {
    }

    EPerInsOrderStatus(String code, String text) {
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
