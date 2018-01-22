package com.xinyunlian.jinfu.product.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by JL on 2016/9/1.
 */
public enum ELoanProductType implements PageEnum {

    CREDIT("01", "信用贷款");

    private String code;

    private String text;

    ELoanProductType(String code, String text) {
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
