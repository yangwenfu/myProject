package com.xinyunlian.jinfu.user.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by JL on 2016/9/8.
 */
public enum  ERelationship implements PageEnum{

    OWNER("OWNER","本人"),

    COUPLE("COUPLE","配偶"),

    PARENT("PARENT", "父母"),

    FATHER("FATHER", "父亲"),

    MOTHER("MOTHER", "母亲"),

    BROTHER_SISTER("BROTHER_SISTER", "兄弟姐妹"),

    FRIEND("FRIEND", "朋友"),

    TRANSFER("TRANSFER", "转让关系"),

    CHILDREN("CHILDREN", "子女"),

    OTHER_RELATIVE("OTHER_RELATIVE", "其他亲戚");


    private String code;

    private String text;

    ERelationship(String code, String text) {
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
