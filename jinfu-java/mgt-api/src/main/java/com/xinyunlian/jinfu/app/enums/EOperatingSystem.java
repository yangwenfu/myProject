package com.xinyunlian.jinfu.app.enums;

/**
 * Created by DongFC on 2016-10-10.
 */
public enum EOperatingSystem {

    IOS("IOS", "iOS"), ANDROID("ANDROID", "安卓");

    private String code;
    private String text;

    EOperatingSystem(String code, String text) {
        this.code = code;
        this.text = text;
    }

    EOperatingSystem() {
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
