package com.xinyunlian.jinfu.ad.enums;

/**
 * Created by dongfangchao on 2016/12/21/0021.
 */
public enum EPosPlatform {

    JINFU_MALL("JINFU_MALL", "金服商城"), YLZG("YLZG", "云联掌柜"), BUDDY("BUDDY","小伙伴"), H5_JUMPER("H5_JUMPER","H5中转页"), CLOUD_CODE("CLOUD_CODE", "云码");

    private String code;

    private String text;

    EPosPlatform(String code, String text) {
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
