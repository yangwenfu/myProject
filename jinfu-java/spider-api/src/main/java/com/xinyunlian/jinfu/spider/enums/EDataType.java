package com.xinyunlian.jinfu.spider.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;
import com.xinyunlian.jinfu.spider.dto.OrderInfo;
import com.xinyunlian.jinfu.spider.dto.UserInfo;

/**
 * Created by bright on 2016/12/8.
 */
public enum EDataType implements PageEnum{
    WELCOME("welcome", "主页", Void.class),
    LOGIN("login", "登陆", Boolean.class),
    USERINFO("userInfo","用户信息", UserInfo.class),
    ORDERS("order","订单信息", OrderInfo.class);

    private String code;
    private String text;
    private Class dataClass;

    EDataType(String code, String text, Class dataClass) {
        this.code = code;
        this.text = text;
        this.dataClass = dataClass;
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

    public Class getDataClass() {
        return dataClass;
    }

    public void setDataClass(Class dataClass) {
        this.dataClass = dataClass;
    }
}
