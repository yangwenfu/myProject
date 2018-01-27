package com.xinyunlian.jinfu.promo.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by bright on 2016/11/21.
 */
public enum EPromoInfStatus implements PageEnum{
    INACTIVE("inactive", "未激活"),
    ACTIVE("active","激活"),
    FINISHED("finished","完成"),
    INVALID("invalid","失效"),
    DELETED("deleted", "已删除");

    private String code;
    private String text;

    EPromoInfStatus(String code, String text) {
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
