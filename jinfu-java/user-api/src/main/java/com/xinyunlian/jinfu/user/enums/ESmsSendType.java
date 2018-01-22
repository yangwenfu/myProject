package com.xinyunlian.jinfu.user.enums;

/**
 * Created by KimLL on 2016/8/29.
 */
public enum ESmsSendType {
    REGISTER("REGISTER_SMS_"),
    FORGET("FORGET_SMS_"),
    BANKCARD("BANKCARD_SMS_"),
    WECHAT_BIND("WECHAT_BIND_SMS_"),
    LOGIN_PWD("LOGIN_PWD_SMS_"),
    DEAL_PWD("DEAL_PWD_SMS_"),
    CLERK_AUTH("CLERK_AUTH_SMS_"),//店员授权
    LOGIN("LOGIN_SMS_"),
    LOAN_DEPOSITORY_REPAY("LOAN_DEPOSITORY_REPAY"), //小贷存管还款
    ACTIVE("ACTIVE_SMS_");

    private String code;

    ESmsSendType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
