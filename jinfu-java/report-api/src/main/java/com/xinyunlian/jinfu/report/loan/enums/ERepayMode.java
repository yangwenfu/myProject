package com.xinyunlian.jinfu.report.loan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

public enum ERepayMode implements PageEnum{

    ONCE_PAY("01", "一次性还本付息"),

    INTR_PER_DIEM("02","随借随还，按日计息"),

    INTR_PER_MENSEM("03","按月付息，到期还本"),

    MONTH_AVE_CAP("04", "按月等额本金"),

    MONTH_AVE_CAP_PLUS_INTR("05", "按月等额本息"),

    QUARTER_AVE_CAP("06", "按季等额本金"),

    QUARTER_AVE_CAP_PLUS_INTR("07", "按季等额本息"),

    ALL("ALL", "全部");

    private String code;

    private String text;

    ERepayMode(String code, String text) {
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
