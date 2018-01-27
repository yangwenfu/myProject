package com.xinyunlian.jinfu.ad.enums;

/**
 * Created by DongFC on 2016-08-24.
 */
public enum EAdType {

    TITLE_AD("TITLE_AD","平铺广告"), CAROUSEL_AD("CAROUSEL_AD", "轮播广告");

    private String code;

    private String text;

    EAdType(String code, String text) {
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
