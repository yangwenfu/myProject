package com.xinyunlian.jinfu.pay.enums;


/**
 * Created by cong on 2016/5/24.
 */
public enum EMsgType{

    REQ("req","请求报文"),
    RESP("resp","响应报文");

    private String code;

    private String text;

    EMsgType(String code, String text){
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
