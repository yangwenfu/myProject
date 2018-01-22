package com.xinyunlian.jinfu.yunma.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by menglei on 2017/1/5.
 */
public enum ERouterAgentStatus implements PageEnum {

    ENABLE("0", "正常"),
    DISABLE("1", "停用");

    private String code;

    private String text;

    ERouterAgentStatus(String code, String text) {
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
