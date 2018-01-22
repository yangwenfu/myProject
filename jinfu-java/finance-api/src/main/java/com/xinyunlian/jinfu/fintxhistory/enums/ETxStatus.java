package com.xinyunlian.jinfu.fintxhistory.enums;

/**
 * Created by dongfangchao on 2016/11/22.
 */
public enum ETxStatus {

    SUCCESS("SUCCESS","成功"), INPROCESS("INPROCESS","处理中"), FAILURE("FAILURE", "失败");

    private String code;
    private String text;

    ETxStatus(String code, String text) {
        this.code = code;
        this.text = text;
    }

    ETxStatus() {
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
