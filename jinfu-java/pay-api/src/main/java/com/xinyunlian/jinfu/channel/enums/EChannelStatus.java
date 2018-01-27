package com.xinyunlian.jinfu.channel.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by bright on 2016/11/18.
 */
public enum EChannelStatus implements PageEnum {

    DISABLED("0","停用"), ENABLED("1", "启用");

    private String code;

    private String text;

    EChannelStatus(String code, String text) {
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