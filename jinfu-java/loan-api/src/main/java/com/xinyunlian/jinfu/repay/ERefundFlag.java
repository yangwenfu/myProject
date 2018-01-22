package com.xinyunlian.jinfu.repay;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * @author willwang
 */
public enum ERefundFlag implements PageEnum {

    ALL("ALL","全部"),
    WHOLE("1","已还款完毕"),
    PART("2", "未还款完毕");

    private String code;

    private String text;

    ERefundFlag(String code, String text){
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