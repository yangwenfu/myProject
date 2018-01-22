package com.xinyunlian.jinfu.yunma.enums;

/**
 * Created by menglei on 2017年2月21日.
 */
public enum EMemberAuditStatus {

    SUCCESS("SUCCESS","审核成功"),
    AUDITING("AUDITING","审核中"),
    UNAUDIT("UNAUDIT","未审核"),
    FAILED("FAILED","审核失败");

    private String code;
    private String text;

    EMemberAuditStatus() {
    }

    EMemberAuditStatus(String code, String text) {
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
