package com.xinyunlian.jinfu.cms.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by dongfangchao on 2017/1/24/0024.
 */
public enum ENoticePlatform implements PageEnum{

    JINFU_MALL("JINFU_MALL", "金服商城"), YLZG("YLZG", "云联掌柜"), BUDDY("BUDDY", "小伙伴");

    private String code;

    private String text;

    ENoticePlatform(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
