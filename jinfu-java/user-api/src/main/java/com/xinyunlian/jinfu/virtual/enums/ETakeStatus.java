package com.xinyunlian.jinfu.virtual.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by KimLL on 2016/8/31.
 */
public enum ETakeStatus implements PageEnum {
    INITIAL("INITIAL", "初始"),
    TAKED("TAKED", "已领用"),
    USED ("USED", "已使用");

    private String code;

    private String text;

    ETakeStatus(String code, String text) {
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
