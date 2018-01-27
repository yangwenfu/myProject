package com.xinyunlian.jinfu.dealer.enums;

/**
 * Created by menglei on 2016年08月26日.
 */
public enum EDealerStatus {

    NORMAL("NORMAL", "正常"),
    FROZEN("FROZEN", "冻结"),
    DELETE("DELETE", "删除");

    private String code;

    private String text;

    EDealerStatus(String code, String text) {
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
