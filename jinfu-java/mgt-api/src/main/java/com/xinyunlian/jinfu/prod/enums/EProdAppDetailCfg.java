package com.xinyunlian.jinfu.prod.enums;

/**
 * Created by dongfangchao on 2017/1/25/0025.
 */
public enum EProdAppDetailCfg {

    RCMD("RCMD", "推荐"), NEW("NEW", "新品"), HOT("HOT", "热卖");

    private String code;

    private String text;

    EProdAppDetailCfg(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
