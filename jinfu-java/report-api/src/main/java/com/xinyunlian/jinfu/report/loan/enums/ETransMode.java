package com.xinyunlian.jinfu.report.loan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum ETransMode implements PageEnum{

    ALL("ALL", "全部"),
    COUNTER("1", "收银台支付"),
    AUTO("2", "代扣");

    private String code;

    private String text;

    ETransMode(String code, String text) {
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
