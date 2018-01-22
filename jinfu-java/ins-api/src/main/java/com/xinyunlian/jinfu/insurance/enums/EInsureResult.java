package com.xinyunlian.jinfu.insurance.enums;

/**
 * Created by DongFC on 2016-09-26.
 */
public enum EInsureResult {


    SUCCESS("1001", "成功"), FAIL("2001", "失败");

    public String code;
    public String description;

    EInsureResult(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
