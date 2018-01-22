package com.xinyunlian.jinfu.ad.enums;

/**
 * Created by dongfangchao on 2016/12/21/0021.
 */
public enum EAdStatus {

    NORMAL("NORMAL", "正常"), DELETE("DELETE","已删除");

    private String code;

    private String text;

    EAdStatus(String code, String text) {
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
