package com.xinyunlian.jinfu.store.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by KimLL on 2016/8/31.
 */
public enum EEstate implements PageEnum {
    OWNER("1", "自有"),
    RENT("2", "租用"),
    OTHER("3", "其他");

    private String code;

    private String text;

    EEstate(String code, String text) {
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
