package com.xinyunlian.jinfu.enums;

/**
 * Created by cong on 2016/6/8.
 */
public enum ETranType {

    PAY("1001"), RECV("1002"), BATCH_PAY("1003");

    ETranType(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

}