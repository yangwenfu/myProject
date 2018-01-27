package com.xinyunlian.jinfu.yunma.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by jll on 2016/8/19.
 */
public enum EMemberStatus implements PageEnum {

    ENABLE("0", "已启用"),
    DISABLE("1", "已停用"),
    UNBIND("2", "已解绑"),
    DELETE("3", "已删除");

    private String code;

    private String text;

    EMemberStatus(String code, String text) {
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
