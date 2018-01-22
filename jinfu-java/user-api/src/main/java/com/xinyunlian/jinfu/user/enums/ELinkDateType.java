package com.xinyunlian.jinfu.user.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by JL on 2016/9/8.
 */
public enum ELinkDateType implements PageEnum{

    OWN("0","用户所有"),
    MGT("1", "风控所有");

    private String code;

    private String text;

    ELinkDateType(String code, String text) {
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
