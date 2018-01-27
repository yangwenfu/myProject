package com.xinyunlian.jinfu.overdue.enums;

public enum EOverdueInfoStatus {

    NORMAL("正常"),

    PAID("已结清");

    private String text;

    EOverdueInfoStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
