package com.xinyunlian.jinfu.zrfundstx.enums;

/**
 * Created by dongfangchao on 2016/12/22/0022.
 */
public enum ETerminal {

    WEB("WEB", "WEB"), WAP("WAP","WEB"), ANDROID("ANDROID","ANDROID"), IOS("IOS","IOS"), HTML5_CHINA_PAY("HTML5_CHINA_PAY", "HTML5ChinaPay");

    private String code;
    private String text;

    ETerminal(String code, String text) {
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
