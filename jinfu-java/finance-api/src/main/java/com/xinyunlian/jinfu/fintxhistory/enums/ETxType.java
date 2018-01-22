package com.xinyunlian.jinfu.fintxhistory.enums;

/**
 * Created by dongfangchao on 2016/11/22.
 */
public enum ETxType {

    REDEEM("REDEEM","赎回"), APPLY_PUR("APPLY_PUR","申购"), QUERY("QUERY","查询"),
    OPEN_ACC("OPEN_ACC","开户"), SYC_QN("SYC_QN", "行情同步"), BANK_SIGN_APPLY("BANK_SIGN_APPLY", "银行后台签约申请"),
    SUPER_CASH_TRADE_QUERY("SUPER_CASH_TRADE_QUERY", "超级现金宝交易结果查询");

    private String code;
    private String text;

    ETxType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    ETxType() {
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
