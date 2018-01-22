package com.xinyunlian.jinfu.user.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by JLL on 2016/9/7.
 */
public enum ESalaryMode implements PageEnum{

    SELF("SELF", "自雇"),
    SALARY("SALARY", "受薪");


    private String code;

    private String text;

    ESalaryMode(String code, String text) {
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
