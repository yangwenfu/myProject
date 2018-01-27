package com.xinyunlian.jinfu.dealer.enums;

/**
 * Created by menglei on 2016年08月26日.
 */
public enum EDealerUserOrderStatus {

    PROCESS("PROCESS", "处理中"),
    SUCCESS("SUCCESS", "办理成功"),
    ERROR("ERROR", "办理失败");

    private String code;

    private String text;

    EDealerUserOrderStatus(String code, String text) {
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
