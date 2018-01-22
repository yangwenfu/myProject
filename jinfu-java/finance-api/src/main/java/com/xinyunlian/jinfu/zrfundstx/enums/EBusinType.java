package com.xinyunlian.jinfu.zrfundstx.enums;

/**
 * Created by dongfangchao on 2016/11/28/0028.
 */
public enum EBusinType {

    openAccount("openAccount","个人开户"), buyApply("buyApply","个人申购"), staticShareQuerySuper("staticShareQuerySuper","超级现金宝份额查询"),
    redeemApply("redeemApply","个人T+1赎回"), realTimeRedeemApply("realTimeRedeemApply","个人T+0赎回"),
    productInfoQuerySuper("productInfoQuerySuper","超级现金宝产品信息查询"), bankBgSign("bankBgSign", "银行后台签约"),
    transResultQuerySuper("transResultQuerySuper", "超级现金宝交易结果查询");

    private String code;
    private String text;

    EBusinType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    EBusinType() {
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
