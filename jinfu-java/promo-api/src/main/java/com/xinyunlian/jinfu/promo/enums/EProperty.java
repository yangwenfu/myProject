package com.xinyunlian.jinfu.promo.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by jll on 2016/11/21.
 */
public enum EProperty implements PageEnum {
    GIT("git","礼品"),
    DISCOUNT("discount","折扣"),
    COUPON("coupon","优惠券");

    private String code;
    private String text;

    EProperty(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }
}
