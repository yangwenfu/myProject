package com.xinyunlian.jinfu.wechat.enums;

/**
 * Created by menglei on 2016年12月06日.
 */
public enum EWeChatPushType {

    PAY("PAY","支付"),APPLY("APPLY","申请"),AUTH("AUTH","授权"),AUDIT("AUDIT","审核");

    private String code;
    private String text;

    EWeChatPushType() {
    }

    EWeChatPushType(String code, String text) {
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
