package com.xinyunlian.jinfu.loan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by Willwang on 2017/3/10.
 */
public enum EApplChannel implements PageEnum {

    SELF("SELF", "掌柜"),
    UNION("UNION", "聚合APP");

    private String code;

    private String text;

    EApplChannel(String code, String text){
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
