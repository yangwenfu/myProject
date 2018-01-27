package com.xinyunlian.jinfu.ad.enums;

/**
 * Created by dongfangchao on 2017/1/12/0012.
 */
public enum EAdValidStatus {

    FURTURE("FURTURE", "待开始"), USING("USING", "投放中"), OVER_DUE("OVER_DUE", "已过期");

    private String code;
    private String text;

    EAdValidStatus(String code, String text) {
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
