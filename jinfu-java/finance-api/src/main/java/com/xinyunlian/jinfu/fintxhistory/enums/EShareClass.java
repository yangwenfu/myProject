package com.xinyunlian.jinfu.fintxhistory.enums;

/**
 * Created by dongfangchao on 2016/11/29/0029.
 */
public enum EShareClass {

    AFTER_PAID("AFTER_PAIED",1l, "后收费"), BEFORE_PAID("BEFORE_PAID",0l, "前收费");

    private String code;
    private Long value;
    private String desc;

    EShareClass(String code, Long value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    EShareClass() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
