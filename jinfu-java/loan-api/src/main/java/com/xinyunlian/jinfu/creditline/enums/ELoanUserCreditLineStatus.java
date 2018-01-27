package com.xinyunlian.jinfu.creditline.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * @author willwang
 */
public enum ELoanUserCreditLineStatus implements PageEnum {

    NOT_EXISTS("-1", "额度不存在"),
    CALCULATING("0", "额度获取中"),
    UNUSED("1", "未使用"),
    USED("2", "已使用"),
    INVALID("3", "无效");

    private String code;

    private String text;

    ELoanUserCreditLineStatus(String code, String text){
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
