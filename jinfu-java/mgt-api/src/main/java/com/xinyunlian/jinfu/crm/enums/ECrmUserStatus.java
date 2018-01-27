package com.xinyunlian.jinfu.crm.enums;

/**
 * Created by JL on 2016/8/19.
 */
public enum ECrmUserStatus {

    NON_EXISTED("NON_EXISTED", "不存在"),
    NON_ACTIVE("NON_ACTIVE", "未激活"),
    ACTIVE("ACTIVE", "已激活");

    private String code;

    private String text;

    ECrmUserStatus(String code, String text) {
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
