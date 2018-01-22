package com.xinyunlian.jinfu.user.enums;

/**
 * Created by DongFC on 2016-08-24.
 */
public enum EDuty {

    REGIONAL_MGT("REGIONAL_MGT", "区域总监"),
    CITY_MGT("CITY_MGT", "城市经理"),
    CHANNEL_MGT("CHANNEL_MGT", "渠道总部");

    private String code;

    private String text;

    EDuty(String code, String text) {
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
