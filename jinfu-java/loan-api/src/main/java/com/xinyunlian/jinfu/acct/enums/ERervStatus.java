package com.xinyunlian.jinfu.acct.enums;

/**
 * @author cheng
 */
public enum ERervStatus {

    ACTIVATE("A", "激活"),
    CLOSE("C", "关闭"),
    ALL("ALL", "全部");

    private String code;

    private String text;

    ERervStatus(String code, String text) {
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
