package com.xinyunlian.jinfu.router.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * @author willwang
 */
public enum EFinanceSourceType implements PageEnum {

    ALL("ALL", "全部"),
    OWN("OWN", "自有资金"),
    AITOUZI("AITOUZI", "爱投资");

    private String code;

    private String text;

    EFinanceSourceType(String code, String text){
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
