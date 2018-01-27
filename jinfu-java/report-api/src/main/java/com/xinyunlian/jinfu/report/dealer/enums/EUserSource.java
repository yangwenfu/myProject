package com.xinyunlian.jinfu.report.dealer.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by jll on 2016/8/19.
 */
public enum EUserSource implements PageEnum {

    DEALER("0", "分销采集"),
    SYSTEM("1", "系统录入"),
    THIRD_PARTY("2", "第三方系统"),
    REGISTER("3", "自行注册"),
    IMPORT("4", "导入数据");

    private String code;

    private String text;

    EUserSource(String code, String text) {
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
