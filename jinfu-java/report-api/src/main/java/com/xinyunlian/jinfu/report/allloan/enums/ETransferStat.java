package com.xinyunlian.jinfu.report.allloan.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * @author cheng
 */
public enum ETransferStat implements PageEnum{

    ALL("ALL", "全部"),
    PROCESS("01", "放款中"),
    FAILED("11", "放款失败"),
    SUCCESS("02", "放款成功");

    private String code;

    private String text;

    ETransferStat(String code, String text) {
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
