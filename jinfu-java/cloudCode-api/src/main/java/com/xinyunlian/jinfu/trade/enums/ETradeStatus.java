package com.xinyunlian.jinfu.trade.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by king on 2017年01月18日.
 */
public enum ETradeStatus implements PageEnum{

    UNPAID("0","未支付"),
    SUCCESS("1","支付成功"),
    Fail("2","支付失败");

    private String code;

    private String text;

    ETradeStatus(String code, String text) {
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
