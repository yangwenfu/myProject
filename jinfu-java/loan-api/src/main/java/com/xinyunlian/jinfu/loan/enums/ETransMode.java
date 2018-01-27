package com.xinyunlian.jinfu.loan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum ETransMode implements PageEnum{

    ALL("ALL", "全部"),
    COUNTER("1", "收银台还款"),
    AUTO("2", "代扣还款");

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
