package com.xinyunlian.jinfu.bank.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by KimLL on 2016/8/31.
 */
public enum ECardUsage implements PageEnum {
    PAYMENT("01", "支付卡"),
    SETTLEMENT ("02", "结算卡");

    private String code;

    private String text;

    ECardUsage(String code, String text) {
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
