package com.xinyunlian.jinfu.yunma.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by menglei on 2017/1/5.
 */
public enum EUserAgent implements PageEnum {

    DEALER("com.yljf.xhb", "小伙伴"),
    WECHATPAY("micromessenger", "微信支付"),
    ALIPAY("alipay", "支付宝支付"),
    BESTPAY("bestpay", "翼支付"),
    JDPAY("jdpay", "京东支付"),
    QQPAY("qq", "QQ支付"),
    BAIDUPAY("baidu", "百度支付");

    private String code;

    private String text;

    EUserAgent(String code, String text) {
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
