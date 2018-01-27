package com.xinyunlian.jinfu.store.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by KimLL on 2016/8/31.
 */
public enum EStoreType implements PageEnum {
    STORE("1", "便利店"),
    GROCERY ("2", "食杂店"),
    SUPERMARKET("3", "超市"),
    TOBACCO("4", "烟酒商店"),
    AMUSEMENT("5", "娱乐服务"),
    OTHER("6", "其他");

    private String code;

    private String text;

    EStoreType(String code, String text) {
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
