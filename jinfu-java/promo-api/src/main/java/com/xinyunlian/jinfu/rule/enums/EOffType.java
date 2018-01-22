package com.xinyunlian.jinfu.rule.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by JL on 2016/9/5.
 */
public enum EOffType implements PageEnum {

    RATE("RATE","按利率"),
    MONEY("MONEY","按金额");

    private String code;

    private String text;

    EOffType(String code, String text) {
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
