package com.xinyunlian.jinfu.user.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by JL on 2016/9/7.
 */
public enum  EMarryStatus  implements PageEnum {

    HAS_BABY("HAS_BABY","已婚已育"),

    MARRIED("MARRIED", "已婚未育"),

    NOT_MARRIED("NOT_MARRIED", "未婚"),

    DIVORCED("DIVORCED", "离异"),

        SPOUSES_LOSS("SPOUSES_LOSS", "丧偶");


    private String code;

    private String text;

    EMarryStatus(String code, String text) {
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
