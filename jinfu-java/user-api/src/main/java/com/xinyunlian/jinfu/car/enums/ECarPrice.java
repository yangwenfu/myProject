package com.xinyunlian.jinfu.car.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by jll on 2016/8/19.
 */
public enum ECarPrice implements PageEnum {

    TEN("TEN", "0-15万"),
    TWENTY("TWENTY", "15-30万"),
    THIRTY("THIRTY", "30-45万"),
    FORTY("FORTY", "45-60万"),
    FIFTY("FIFTY", "60万以上");

    private String code;

    private String text;

    ECarPrice(String code, String text) {
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
