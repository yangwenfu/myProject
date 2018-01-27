package com.xinyunlian.jinfu.loan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum ERepayType implements PageEnum{

    PRE_ALL("01", "等额本息提前还全部"),
    PRE_CURRENT("02", "等额本息提前还本期"),
    DAY("03", "随借随还"),
    OVERDUE("04", "等额本息逾期还款"),
    PERIOD("05", "等额本息还单期"),
    AVE_CAP_AVG_INTR_REPAY("06","等本等息还款"),
    AVE_CAP_AVG_INTR_REPAY_ALL("07","等本等息全部"),
    ;


    private String code;

    private String text;

    ERepayType(String code, String text) {
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
