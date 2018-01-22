package com.xinyunlian.jinfu.report.loan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * @author cheng
 */
public enum ELoanStat implements PageEnum{

    ALL("ALL", "全部"),
    NORMAL("01", "使用中"),
    PAID("04", "已结清"),
    OVERDUE("11", "已逾期");

    private String code;

    private String text;

    ELoanStat(String code, String text) {
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
