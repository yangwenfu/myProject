package com.xinyunlian.jinfu.fintxhistory.enums;

/**
 * Created by dongfangchao on 2016/11/29/0029.
 */
public enum ERedeemType {

    REDEEM_REAL_TIME("REDEEM_REAL_TIME","实时赎回"), REDEEM_NORMAL("REDEEM_NORMAL","普通赎回");

    private String code;
    private String text;

    ERedeemType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    ERedeemType() {
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
