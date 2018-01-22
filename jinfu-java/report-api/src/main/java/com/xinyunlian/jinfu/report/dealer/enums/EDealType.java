package com.xinyunlian.jinfu.report.dealer.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by DongFC on 2016-09-21.
 */
public enum EDealType implements PageEnum{

    SELFSERVICE("SELFSERVICE","自主办理"), MANAGERSERVICE("MANAGERSERVICE","分销代办");

    private String code;
    private String text;

    EDealType() {
    }

    EDealType(String code, String text) {
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
