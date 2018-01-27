package com.xinyunlian.jinfu.user.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by JL on 2016/9/8.
 */
public enum ELabelType implements PageEnum{

    YM("YM","云码"),
    DPB("DPB", "店铺保"),
    CX("CX", "车险"),
    YYD("YYD", "云易贷"),
    YLYD("YLYD", "云联易贷"),
    ZF("ZF", "支付"),
    MCC5227("5227","烟草店主认证"),
    MCC5699("5699","服装零售店主认证"),
    MCC5812("5812","餐饮餐馆店主认证"),
    MCC7911("7911", "KTV店主认证"),
    MCC7994("7994", "网吧店主认证"),
    MCC7230("7230", "美发店主认证"),
    MCC7298("7298", "美容SPA店主认证"),
    MCC5251("5251", "五金店主认证"),
    MCC5499("5499", "食品零售店主认证"),
    MCC5331("5331", "便利店主认证");

    private String code;

    private String text;

    ELabelType(String code, String text) {
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
