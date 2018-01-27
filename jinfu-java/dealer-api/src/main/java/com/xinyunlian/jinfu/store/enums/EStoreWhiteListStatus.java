package com.xinyunlian.jinfu.store.enums;

/**
 * Created by menglei on 2017年06月20日.
 */
public enum EStoreWhiteListStatus {

    NOSIGNIN("NOSIGNIN", "未签到"),
    SIGNEDIN("SIGNEDIN", "已签到");

    private String code;

    private String text;

    EStoreWhiteListStatus(String code, String text) {
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
