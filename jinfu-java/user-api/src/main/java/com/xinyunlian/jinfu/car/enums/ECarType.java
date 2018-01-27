package com.xinyunlian.jinfu.car.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by jll on 2016/8/19.
 */
public enum ECarType implements PageEnum {

    CHINA("0", "国产"),
    ABROAD("1", "进口");

    private String code;

    private String text;

    ECarType(String code, String text) {
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
