package com.xinyunlian.jinfu.cms.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by bright on 2016/11/21.
 */
public enum EArcPlatform implements PageEnum{
    YLZG("YLZG","云联掌柜"),
    BUDDY("BUDDY","小伙伴"),
    JINFU_MALL("JINFU_MALL","金服商城");

    private String code;
    private String text;

    EArcPlatform(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }
}
