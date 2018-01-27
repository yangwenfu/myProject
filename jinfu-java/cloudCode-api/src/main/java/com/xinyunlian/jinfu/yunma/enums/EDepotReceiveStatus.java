package com.xinyunlian.jinfu.yunma.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by menglei on 2017/8/29.
 */
public enum EDepotReceiveStatus implements PageEnum {

    RECEIVE("RECEIVE", "已领取"),
    UNRECEIVE("UNRECEIVE", "未领取");

    private String code;

    private String text;

    EDepotReceiveStatus(String code, String text) {
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
