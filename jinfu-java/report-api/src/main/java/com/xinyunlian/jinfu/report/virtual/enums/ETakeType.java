package com.xinyunlian.jinfu.report.virtual.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by KimLL on 2016/8/31.
 */
public enum ETakeType implements PageEnum {
    HAND("HAND ", "手工");

    private String code;

    private String text;

    ETakeType(String code, String text) {
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
