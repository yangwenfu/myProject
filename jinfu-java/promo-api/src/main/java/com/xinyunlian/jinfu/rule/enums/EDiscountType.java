package com.xinyunlian.jinfu.rule.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by jll on 2016/9/7.
 */
public enum EDiscountType implements PageEnum {

    INTR_PER_DIEM("01","随借随还，按日计息"),

    AVE_CAP_PLUS_INTR("02", "等额本息");

    private String code;

    private String text;

    EDiscountType(String code, String text) {
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
