package com.xinyunlian.jinfu.zrfundstx.enums;

/**
 * Created by dongfangchao on 2016/12/22/0022.
 */
public enum ESignBizType {

    BIND_CARD("BINDING_CARD", "0", "绑卡"), CHANGE_CARD("CHANGE_CARD", "1", "换卡"), SIGN("SIGN", "2", "签约"), AUTH("AUTH", "3", "认证");

    private String code;
    private String text;
    private String desc;

    ESignBizType(String code, String text, String desc) {
        this.code = code;
        this.text = text;
        this.desc = desc;
    }

    ESignBizType() {
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
