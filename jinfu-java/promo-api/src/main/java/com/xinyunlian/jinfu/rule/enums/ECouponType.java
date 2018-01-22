package com.xinyunlian.jinfu.rule.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by jll on 2017/03/16.
 */
public enum ECouponType implements PageEnum{
    INTEREST("INTEREST","免息券");

    private String code;
    private String text;

    ECouponType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }
}
