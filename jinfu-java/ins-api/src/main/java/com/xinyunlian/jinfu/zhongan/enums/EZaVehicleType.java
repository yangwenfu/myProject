package com.xinyunlian.jinfu.zhongan.enums;

/**
 * Created by dongfangchao on 2017/3/21/0021.
 */
public enum EZaVehicleType {

    BIZINS("1", "商业"), COMPELINS("2","交强");

    private String code;

    private String text;

    EZaVehicleType(String code, String text) {
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
