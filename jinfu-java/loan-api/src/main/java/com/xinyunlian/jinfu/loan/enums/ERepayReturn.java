package com.xinyunlian.jinfu.loan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum ERepayReturn implements PageEnum{

    ALL("ALL", "全部"),
    PROCESS("0", "处理中"),
    SUCCESS("1", "成功"),
    FAILED("2", "失败");

    private String code;

    private String text;

    ERepayReturn(String code, String text) {
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
