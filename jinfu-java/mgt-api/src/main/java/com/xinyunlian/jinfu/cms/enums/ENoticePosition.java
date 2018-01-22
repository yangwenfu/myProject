package com.xinyunlian.jinfu.cms.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by dongfangchao on 2017/5/18/0018.
 */
public enum ENoticePosition implements PageEnum {

    GLOBAL_SITE_NOTICE("GLOBAL_SITE_NOTICE", "全站公告"), USER_CENTER_NOTICE("USER_CENTER_NOTICE", "用户中心公告");

    private String code;

    private String text;

    ENoticePosition(String code, String text) {
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
