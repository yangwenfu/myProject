package com.xinyunlian.jinfu.user.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by JLL on 2016/9/7.
 */
public enum EIncomeSource implements PageEnum{

    SALARY("SALARY", "工资"),

    SELF("SELF", "个体经营"),

    INVEST("INVEST", "投资"),

    LEASE("LEASE", "租赁"),

    OTHER("OTHER", "其他");


    private String code;

    private String text;

    EIncomeSource(String code, String text) {
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
