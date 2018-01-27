package com.xinyunlian.jinfu.dealer.enums;

/**
 * Created by menglei on 2017年05月03日.
 */
public enum EDealerAuditStatus {

    UN_FIRST_AUDIT("UN_FIRST_AUDIT", "待大区经理审批"),
    UN_LAST_AUDIT("UN_LAST_AUDIT", "待总部审批"),
    PASS("PASS", "通过"),
    REFUSE("REFUSE", "已拒绝");

    private String code;

    private String text;

    EDealerAuditStatus(String code, String text) {
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
