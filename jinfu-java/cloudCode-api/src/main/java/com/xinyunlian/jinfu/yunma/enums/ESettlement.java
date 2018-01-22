package com.xinyunlian.jinfu.yunma.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by jll on 2016/8/19.
 */
public enum ESettlement implements PageEnum {

    D0("0", "D+0"),
    T1("1", "T+1");

    private String code;

    private String text;

    ESettlement(String code, String text) {
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
