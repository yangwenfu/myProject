package com.xinyunlian.jinfu.acct.enums;

/**
 * @author cheng
 */
public enum ERecvPayType {

    RECV("R", "收"),
    PAY("P", "付"),
    ALL("ALL", "全部");

    private String code;

    private String text;

    ERecvPayType(String code, String text) {
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
