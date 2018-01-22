package com.xinyunlian.jinfu.trade.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by King on 2016年11月20日.
 */
public enum EBizCode implements PageEnum{

    WECHAT("1005","微信"),
    ALLIPAY ("2003","支付宝"),
    CMCC("2016","中移积分"),
    JDPAY("JDPAY","京东支付"),
    BESTPAY("BESTPAY","翼支付"),
    QQPAY("QQPAY","QQ支付"),
    BAIDUPAY("BAIDUPAY","百度支付"),
    PAYCODE("PAYCODE","支付码"),
    UNKNOW("0000","未知");

    private String code;
    private String text;

    EBizCode(String code, String text) {
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
