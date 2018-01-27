package com.xinyunlian.jinfu.zhongan.enums;

/**
 * Created by dongfangchao on 2017/3/21/0021.
 */
public enum EZaNotifyType {

    INSURE("1", "投保"), CORRECTING("2", "批改"), SURRENDER("3", "退保");

    private String code;

    private String text;

    EZaNotifyType(String code, String text) {
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
