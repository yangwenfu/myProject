package com.xinyunlian.jinfu.promo.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by jll on 2016/11/21.
 */
public enum EUserCouponStatus implements PageEnum{
    UNUSED("unused", "未使用"),
    USED("used","已使用"),
    OVERDUE("overdue","已过期");

    private String code;
    private String text;

    EUserCouponStatus(String code, String text) {
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
