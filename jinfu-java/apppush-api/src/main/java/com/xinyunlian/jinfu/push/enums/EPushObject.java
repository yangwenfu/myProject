package com.xinyunlian.jinfu.push.enums;

/**
 * Created by apple on 2017/7/24.
 */
public enum EPushObject {

    ALL("ALL", 0),
    XHB("XHB", 1),
    ZG("ZG", 2);

    private String code;

    private Integer value;

    EPushObject(String code, Integer value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}