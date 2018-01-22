package com.xinyunlian.jinfu.external.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by godslhand on 2017/6/20.
 */
public enum PaymentOptionType implements PageEnum {
    once("1", "到期一次性"), seven_period("2", "每7天还"),
    fourteen_period("3", "每14天还"), thirty_period("4", "每30天还");

    private String code;
    private String text;

    PaymentOptionType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }
}
