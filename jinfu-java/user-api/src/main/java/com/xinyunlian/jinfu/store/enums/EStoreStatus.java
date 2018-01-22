package com.xinyunlian.jinfu.store.enums;

/**
 * Created by KimLL on 2016/8/31.
 */
public enum EStoreStatus {
    NORMAL("0"),
    DELETE("1");

    private String code;

    EStoreStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
