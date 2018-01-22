package com.xinyunlian.jinfu.user.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by JL on 2016/9/7.
 */
public enum ELinkManOrder implements PageEnum{

    ONE("ONE", "第一联系人"),

    TWO("TWO", "第二联系人"),

    THREE("THREE", "第三联系人"),

    FOUR("FOUR", "第四联系人");


    private String code;

    private String text;

    ELinkManOrder(String code, String text) {
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
