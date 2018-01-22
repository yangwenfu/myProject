package com.xinyunlian.jinfu.payCode.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by carrot on 2017/8/28.
 */
public enum PayCodeBalanceType implements PageEnum {

    RECHARGE("RECHARGE", "充值"), PAY("PAY", "支付");

    private String code;
    private String text;

    PayCodeBalanceType(String code, String text) {
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
