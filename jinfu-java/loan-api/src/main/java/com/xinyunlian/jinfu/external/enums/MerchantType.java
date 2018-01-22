package com.xinyunlian.jinfu.external.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by godslhand on 2017/7/1.
 */
public enum MerchantType implements PageEnum {
    cigarette ("1","烟酒商店"),market("2","超市"),store("3","便利店"),shop("4","杂食店");

    private String code;

    private String text;

    MerchantType(String code, String text){
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }
}
