package com.xinyunlian.jinfu.pay.enums;

/**
 * @author cheng
 */
public enum EOrdStatus {

    SUCCESS("S", "成功"), FAILED("F", "失败"), PROCESS("P", "处理中"), ALL("ALL", "全部");

    private String code;

    private String text;

    EOrdStatus(String code, String text) {
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
