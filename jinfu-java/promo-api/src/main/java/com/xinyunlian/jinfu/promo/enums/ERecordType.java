package com.xinyunlian.jinfu.promo.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by bright on 2016/11/21.
 */
public enum ERecordType implements PageEnum {
    WHITE_RECORD("WHITE_RECORD", "白名单"),
    BLACK_RECORD("BLACK_RECORD", "黑名单");

    private String code;
    private String text;

    ERecordType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }
}
