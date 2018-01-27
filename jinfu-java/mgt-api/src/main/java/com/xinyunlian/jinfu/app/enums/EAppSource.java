package com.xinyunlian.jinfu.app.enums;

/**
 * Created by DongFC on 2016-10-08.
 */
public enum EAppSource {

    BUDDY("BUDDY","小伙伴", "xiaohuoban"), YLZG("YLZG", "云联掌柜", "ylzg");

    private String code;
    private String text;
    private String app;

    EAppSource(String code, String text, String app) {
        this.code = code;
        this.text = text;
        this.app = app;
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

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
