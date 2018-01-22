package com.xinyunlian.jinfu.yunma.enums;

/**
 * Created by menglei on 2017-01-09.
 */
public enum EYmProd {

    JF0001("JF0001", "中移积分"),
    YM0001("YM0001", "云码");

    private String code;
    private String text;

    EYmProd(String code, String text) {
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
