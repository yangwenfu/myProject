package com.xinyunlian.jinfu.user.enums;

/**
 * Created by DongFC on 2016-08-24.
 */
public enum EMgtUserStatus {

    NORMAL("NORMAL", "正常"),
    FROZEN("FROZEN", "冻结"),
    DELETE("DELETE", "已删除");

    private String code;

    private String text;

    EMgtUserStatus(String code, String text) {
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
