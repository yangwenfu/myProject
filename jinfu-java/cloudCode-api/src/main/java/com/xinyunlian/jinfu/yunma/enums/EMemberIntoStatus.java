package com.xinyunlian.jinfu.yunma.enums;

/**
 * Created by menglei on 2017年7月19日.
 */
public enum EMemberIntoStatus {

    INTO_SUCCESS("INTO_SUCCESS","进件成功"),
    INTO_ING("INTO_ING","进件中"),
    INTO_FAILED("INTO_FAILED","进件失败");

    private String code;
    private String text;

    EMemberIntoStatus() {
    }

    EMemberIntoStatus(String code, String text) {
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
