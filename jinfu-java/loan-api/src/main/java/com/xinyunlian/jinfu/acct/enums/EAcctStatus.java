package com.xinyunlian.jinfu.acct.enums;

/**
 * 帐户状态
 *
 * @author cheng
 */
public enum EAcctStatus {

    NORMAL("0", "正常"),
    FROZEN("1", "冻结"),
    ALL("ALL", "全部");

    private String code;

    private String text;

    EAcctStatus(String code, String text) {
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
