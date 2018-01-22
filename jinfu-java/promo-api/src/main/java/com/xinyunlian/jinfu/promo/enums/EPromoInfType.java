package com.xinyunlian.jinfu.promo.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by bright on 2016/11/21.
 */
public enum EPromoInfType implements PageEnum {
    DISCOUNT_ON_FIRST_ORDER("on_first_order","首笔促销"),
    DISCOUNT_ON_TOTAL_AMT("on_total_amt","满减促销"),
    DISCOUNT_ON_COUPON("on_coupon","优惠券促销");

    private String code;
    private String text;

    EPromoInfType(String code, String text) {
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
