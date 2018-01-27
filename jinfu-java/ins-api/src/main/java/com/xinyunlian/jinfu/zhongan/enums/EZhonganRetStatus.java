package com.xinyunlian.jinfu.zhongan.enums;

/**
 * Created by dongfangchao on 2016/12/5/0005.
 */
public enum EZhonganRetStatus {

    SUCCESS("0", "成功"), FAIL("1", "失败");

    public String code;
    public String description;

    EZhonganRetStatus(String code, String description) {
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
