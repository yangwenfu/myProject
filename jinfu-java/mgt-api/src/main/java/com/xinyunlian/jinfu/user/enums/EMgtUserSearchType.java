package com.xinyunlian.jinfu.user.enums;

/**
 * Created by dongfangchao on 2016/12/7/0007.
 */
public enum EMgtUserSearchType {

    DIRECT("DIRECT", "直属部门的员工"),
    INCLUDE_SUB_DEPT("INCLUDE_SUB_DEPT", "包括子部门下的员工");

    private String code;
    private String text;

    EMgtUserSearchType(String code, String text) {
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
