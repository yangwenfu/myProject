package com.xinyunlian.jinfu.thirdparty.nbcb.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by bright on 2017/5/15.
 */
public enum ENBCBLoanStatus implements PageEnum {
    PAIDOFF("PAIDOFF","结清"),
    NOMRAL("NOMRAL", "正常"),
    OVERDUE("OVERDUE", "逾期");

    private String code;
    private String text;

    ENBCBLoanStatus(String code, String text) {
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
