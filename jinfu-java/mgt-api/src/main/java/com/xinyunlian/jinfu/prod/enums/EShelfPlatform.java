package com.xinyunlian.jinfu.prod.enums;

/**
 * Created by dongfangchao on 2016/12/5/0005.
 */
public enum EShelfPlatform {

    JINFU_MALL("JINFU_MALL","金服商城"), YLZG("YLZG","云联掌柜"), BUDDY("BUDDY", "小伙伴"), AIO("AIO", "聚合");

    private String code;
    private String text;

    EShelfPlatform(String code, String text) {
        this.code = code;
        this.text = text;
    }

    EShelfPlatform() {
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
