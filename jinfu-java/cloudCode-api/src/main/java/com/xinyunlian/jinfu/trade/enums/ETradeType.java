package com.xinyunlian.jinfu.trade.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by menglei on 2017年06月01日.
 */
public enum ETradeType implements PageEnum{

    USER_SCAN("USER_SCAN","用户扫码"),
    MERCHANT_SCAN("MERCHANT_SCAN","商家扫码");

    private String code;

    private String text;

    ETradeType(String code, String text) {
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
