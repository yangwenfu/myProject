package com.xinyunlian.jinfu.finaccbankcard.enums;

/**
 * Created by dongfangchao on 2016/11/28/0028.
 */
public enum EFinOpenAccType {

    EXISTS_BANK_CARD_BIND("EXISTS_BANK_CARD_BIND","金服已有银行卡开户"), NORMAL_BANK_CARD_BIND("NORMAL_BANK_CARD_BIND","普通银行卡开户");

    private String code;
    private String text;

    EFinOpenAccType(String code, String text) {
        this.code = code;
        this.text = text;
    }

    EFinOpenAccType() {
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
