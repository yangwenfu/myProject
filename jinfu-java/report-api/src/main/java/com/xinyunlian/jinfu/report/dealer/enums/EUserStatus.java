package com.xinyunlian.jinfu.report.dealer.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by bright on 2017/1/4.
 */
public enum EUserStatus implements PageEnum{
    INACTIVE("Inactive", "未激活"),
    ACTIVE("Active", "激活");

    String code;
    String text;

    EUserStatus(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }
}
