package com.xinyunlian.jinfu.yunma.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by menglei on 2017/8/29.
 */
public enum EDepotStatus implements PageEnum {

    UNBIND_UNUSE("UNBIND_UNUSE", "未绑定未领用"),
    UNBIND_USE("UNBIND_USE", "未绑定已领用"),
    BIND_UNUSE("BIND_UNUSE", "已绑定未领用"),
    BIND_USE("BIND_USE", "已绑定已领用");

    private String code;

    private String text;

    EDepotStatus(String code, String text) {
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
