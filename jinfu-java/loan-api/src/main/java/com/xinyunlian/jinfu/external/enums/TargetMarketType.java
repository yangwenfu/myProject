package com.xinyunlian.jinfu.external.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by godslhand on 2017/7/1.
 */
public enum TargetMarketType implements PageEnum {
    city("1","城市"), town("2","城镇"),village("3","农村");

    private String code;

    private String text;

    TargetMarketType(String code, String text){
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }
}
