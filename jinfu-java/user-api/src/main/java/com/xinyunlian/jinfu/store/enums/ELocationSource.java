package com.xinyunlian.jinfu.store.enums;

/**
 * Created by menglei on 2017/6/21.
 */
public enum ELocationSource {

    CONVERT("CONVERT", "地址转换"),
    LOCATION("LOCATION", "定位获取");

    private String code;

    private String text;

    ELocationSource(String code, String text) {
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
