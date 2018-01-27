package com.xinyunlian.jinfu.insurance.enums;

/**
 * Created by DongFC on 2016-09-21.
 */
public enum EPerInsDealType {

    SELFSERVICE("SELFSERVICE","自主办理"), MANAGERSERVICE("MANAGERSERVICE","业务经理代办"), JINFUSERVICE("JINFUSERVICE", "金服代办");

    private String code;
    private String text;

    EPerInsDealType() {
    }

    EPerInsDealType(String code, String text) {
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
