package com.xinyunlian.jinfu.user.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by jll on 2016/8/19.
 */
public enum ETradeType implements PageEnum {

    DRAW("DRAW", "支取"),
    INCOME("INCOME", "收入");

    private String code;

    private String text;

    ETradeType(String code, String text) {
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
