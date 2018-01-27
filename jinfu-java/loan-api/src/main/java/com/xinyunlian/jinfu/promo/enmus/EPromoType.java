package com.xinyunlian.jinfu.promo.enmus;

/**
 * Created by JL on 2016/11/21.
 */
public enum EPromoType {

    MONEY("1", "金额优惠"), RATE("2", "利率优惠"), OFFLINE("3", "线下优惠");

    private String code;

    private String text;

    EPromoType(String code, String text) {
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