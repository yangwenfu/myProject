package com.xinyunlian.jinfu.user.enums;

/**
 * Created by dongfangchao on 2016/12/26/0026.
 */
public enum EMgtSmsType {

    FORGET("FORGET_SMS_"),
    BANKCARD("BANKCARD_SMS_"),
    LOGIN_PWD("LOGIN_PWD_SMS_"),
    ACTIVE("ACTIVE_SMS_");

    private String code;

    EMgtSmsType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
