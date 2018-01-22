package com.xinyunlian.jinfu.bank.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by KimLL on 2016/8/31.
 */
public enum ECardType implements PageEnum {
    DEBIT("1", "借记卡"),
    CREDIT("2", "信用卡");

    private String code;

    private String text;

    ECardType(String code, String text) {
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
