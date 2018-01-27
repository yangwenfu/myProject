package com.xinyunlian.jinfu.fintxhistory.enums;

/**
 * Created by dongfangchao on 2016/11/29/0029.
 */
public enum ETradeBusinType {

    SUPER_CASH("SUPER_CASH","02","超级现金宝申购");

    private String code;
    private String value;
    private String desc;

    ETradeBusinType(String code, String value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    ETradeBusinType() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
