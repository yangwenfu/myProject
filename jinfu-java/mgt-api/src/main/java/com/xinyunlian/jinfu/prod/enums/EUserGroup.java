package com.xinyunlian.jinfu.prod.enums;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
public enum EUserGroup {

    B_TERMINAL("B_TERMINAL","B端"), C_TERMINAL("C_TERMINAL","C端");

    private String code;
    private String text;

    EUserGroup(String code, String text) {
        this.code = code;
        this.text = text;
    }

    EUserGroup() {
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
