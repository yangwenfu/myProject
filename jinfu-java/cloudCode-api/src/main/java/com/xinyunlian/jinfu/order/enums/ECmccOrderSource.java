package com.xinyunlian.jinfu.order.enums;

/**
 * Created by menglei on 2016年11月20日.
 */
public enum ECmccOrderSource {

    MSKJ("MSKJ","慕尚科技");

    private String code;
    private String text;

    ECmccOrderSource() {
    }

    ECmccOrderSource(String code, String text) {
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
