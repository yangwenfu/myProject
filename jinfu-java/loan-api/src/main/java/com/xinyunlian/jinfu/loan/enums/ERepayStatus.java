package com.xinyunlian.jinfu.loan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum ERepayStatus implements PageEnum{

    ALL("ALL", "全部"),
    PROCESS("0", "处理中"),
    SUCCESS("1", "还款成功"),
    FAILED("2", "还款失败"),
    PAY("3", "待还款");

    private String code;

    private String text;

    ERepayStatus(String code, String text) {
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
