package com.xinyunlian.jinfu.schedule.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * @author willwang
 */
public enum ELoanCustomerStatus implements PageEnum {

    DEALING("01","审核中"), //申请金额 1
    NEEDSURE("02", "需确认"), //放款金额 1
    PROCESS("03","放款中"),
    USE("04", "使用中"),//1
    NOTYET("11", "未还款"),
    PAID("12","已结清"),
    OVERDUE("13","已逾期"),//1
    REJECT("21", "已拒绝"), //申请金额 1
    FALLBACK("22", "已退回"), //申请金额 1
    CANCEL("23", "已取消"), //申请金额1
    ERROR("99", "异常"),
    ALL("98", "全部");

    private String code;

    private String text;

    ELoanCustomerStatus(String code, String text) {
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
