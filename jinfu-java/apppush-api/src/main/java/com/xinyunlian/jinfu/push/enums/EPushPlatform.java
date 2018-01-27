package com.xinyunlian.jinfu.push.enums;

/**
 * Created by apple on 2017/7/24.
 */
public enum EPushPlatform {
    ALL("ALL", 0),
    IOS("IOS", 1),
    ANDROID("ANDROID", 2);

    private String code;

    private Integer value;

    EPushPlatform(String code, Integer value) {
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
