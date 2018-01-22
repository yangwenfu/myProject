package com.xinyunlian.jinfu.payCode.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by carrot on 2017/8/28.
 */
public enum PayCodeStatus implements PageEnum {

    ENABLE("ENABLE", "已启用"), FROZEN("FROZEN", "已冻结"), DISABLE("DISABLE", "已停用");

    private String code;
    private String text;

    PayCodeStatus(String code, String text) {
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
