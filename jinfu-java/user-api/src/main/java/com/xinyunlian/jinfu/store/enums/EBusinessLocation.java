package com.xinyunlian.jinfu.store.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by KimLL on 2016/8/31.
 */
public enum EBusinessLocation implements PageEnum {
    CITY("1", "城市小区"),
    STREET("2", "城市街道"),
    VILLAGE_IN_CITY("3", "城中村"),
    town("4", "县/镇街道"),
    COMMUNITY("5", "乡镇社区"),
    VILLAGE("6", "农村");

    private String code;

    private String text;

    EBusinessLocation(String code, String text) {
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
