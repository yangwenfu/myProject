package com.xinyunlian.jinfu.user.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by JL on 2016/9/7.
 */
public enum EHouseProperty implements PageEnum{

    OWN("OWN", "自有房产无按揭"),

    MORTGAGE("MORTGAGE", "自有房产有按揭"),

    TENANCY("TENANCY", "租用房产"),

    OTHER("OTHER", "其他");


    private String code;

    private String text;

    EHouseProperty(String code, String text) {
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
