package com.xinyunlian.jinfu.report.dealer.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by menglei on 2016年08月26日.
 */
public enum EDealerUserLogType implements PageEnum{

    REGISTER("REGISTER", "注册"),
    ADDSTORE("ADDSTORE", "店铺添加"),
    CERTIFY("CERTIFY", "实名认证"),
    ORDER("ORDER", "业务办理"),
    ADDCARD("ADDCARD", "银行卡添加"),
    ADDNOTE("ADDNOTE", "记录添加");

    private String code;

    private String text;

    EDealerUserLogType(String code, String text) {
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
