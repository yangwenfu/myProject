package com.xinyunlian.jinfu.contract.enums;

/**
 * Created by dongfangchao on 2017/2/14/0014.
 */
public enum EBsSignType {

    ENTERPRISE("ENTERPRISE","公司"), PERSONAL("PERSONAL", "个人");

    private String code;

    private String text;

    EBsSignType(String code, String text) {
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
