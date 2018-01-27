package com.xinyunlian.jinfu.dealer.enums;

/**
 * Created by menglei on 2017年05月09日.
 */
public enum EDealerOpLogType {

    CREATE("CREATE", "创建"),
    FIRST_AUDIT("FIRST_AUDIT", "初审结果"),
    LAST_AUDIT("LAST_AUDIT", "终审结果"),
    RESET("RESET", "重新提交"),
    UPDATE("UPDATE", "修改信息");

    private String code;

    private String text;

    EDealerOpLogType(String code, String text) {
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
