package com.xinyunlian.jinfu.config.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by bright on 2017/1/6.
 */
public enum ECategory implements PageEnum{
    SPIDER("Spider", "爬虫配置");

    private String code;
    private String text;

    ECategory(String code, String text) {
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
