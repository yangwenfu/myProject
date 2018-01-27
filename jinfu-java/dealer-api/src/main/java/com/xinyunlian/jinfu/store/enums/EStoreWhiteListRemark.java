package com.xinyunlian.jinfu.store.enums;

/**
 * Created by menglei on 2017年07月02日.
 */
public enum EStoreWhiteListRemark {

    DEMAND("DEMAND", "有需求"),
    UNWANTED("UNWANTED", "不需要"),
    NOTONESELF("NOTONESELF", "非本人"),
    MISSING("MISSING", "联系不上"),
    REGISTERED("REGISTERED", "他人已办理"),
    NOONE("", "");

    private String code;

    private String text;

    EStoreWhiteListRemark(String code, String text) {
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
