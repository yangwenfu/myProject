package com.xinyunlian.jinfu.external.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by godslhand on 2017/6/19.
 */
public enum ConfirmationType implements PageEnum {
    agree("1","同意"), cancle("2","取消"),overdue("3","过期");
    private String code;

    private String text;

    ConfirmationType(String code, String text){
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
