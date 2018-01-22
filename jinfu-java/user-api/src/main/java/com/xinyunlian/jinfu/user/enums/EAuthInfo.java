package com.xinyunlian.jinfu.user.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by King on 2017/2/15.
 */
public enum EAuthInfo implements PageEnum {

    TOBACCO("1", "烟草店主认证");


    private String code;

    private String text;

    EAuthInfo(String code, String text) {
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
