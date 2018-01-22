package com.xinyunlian.jinfu.channel.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by bright on 2016/11/18.
 */
public enum EChannelType implements PageEnum {

    REAL_AUTH("REAL_AUTH","实名认证");

    private String code;

    private String text;

    EChannelType(String code, String text) {
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
