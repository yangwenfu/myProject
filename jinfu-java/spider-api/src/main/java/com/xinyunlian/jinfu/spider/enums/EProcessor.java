package com.xinyunlian.jinfu.spider.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by bright on 2016/12/27.
 */
public enum EProcessor implements PageEnum{
    XML("XML", "XML"),
    HTML("HTML", "HTML"),
    CAPTCHA("CAPTCHA", "CAPTCHA"),
    ANHUI_ORDER_JSON("ANHUI_ORDER_JSON","ANHUI_ORDER_JSON"),
    HTML_ELEMENT_EXIST("HTML_ELEMENT_EXIST","HTML_ELEMENT_EXIST");

    private String code;
    private String text;

    EProcessor(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }
}
