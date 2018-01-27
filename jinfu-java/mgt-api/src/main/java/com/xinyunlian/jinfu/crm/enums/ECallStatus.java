package com.xinyunlian.jinfu.crm.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by JLL on 2016/8/19.
 */
public enum ECallStatus implements PageEnum {

    PROCESS("PROCESS", "处理中"),
    FINISHED("FINISHED", "完成"),
    DELAY("DELAY", "暂缓处理");

    private String code;

    private String text;

    ECallStatus(String code, String text) {
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
