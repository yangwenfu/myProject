package com.xinyunlian.jinfu.system.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by menglei on 2017/6/12.
 */
public enum ESysConfigType implements PageEnum {

    STORE_ADDRESS("STORE_ADDRESS", "店铺地址评分");

    private String code;

    private String text;

    ESysConfigType(String code, String text) {
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
