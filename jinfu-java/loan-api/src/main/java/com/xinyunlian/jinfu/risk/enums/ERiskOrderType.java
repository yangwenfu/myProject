package com.xinyunlian.jinfu.risk.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * @author willwang
 */
public enum ERiskOrderType implements PageEnum{

    PRESENT("1", "本月"),
    LAST("2", "上月"),
    HISTORY("3", "历史");

    private String code;

    private String text;

    ERiskOrderType(String code, String text) {
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
