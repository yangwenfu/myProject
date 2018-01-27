package com.xinyunlian.jinfu.contract.enums;

/**
 * @author willwang
 */
public enum ESignedMark {

    //0:合同初始化,1:爱投资盖章已完成,2:手动签署已完成

    INIT("0", "合同初始化"),
    SEAL("1", "盖章已完成"),
    THIRD_PARTY("3", "第三方盖章已完成"),
    FIRST_SIGN("2", "手动签署已完成");

    private String code;

    private String text;

    ESignedMark(String code, String text) {
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
