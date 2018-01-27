package com.xinyunlian.jinfu.app.enums;

/**
 * Created by menglei on 2016-12-14.
 */
public enum EDataType {

    AREA("AREA","省市区");

    private String code;
    private String text;

    EDataType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    EDataType() {
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
