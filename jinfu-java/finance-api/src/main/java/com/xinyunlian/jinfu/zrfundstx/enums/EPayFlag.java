package com.xinyunlian.jinfu.zrfundstx.enums;

/**
 * Created by dongfangchao on 2017/8/4/0004.
 */
public enum EPayFlag {

    NOT_CHECKED("NOT_CHECKED", 0, "未校验"), INVALID("INVALID", 1, "无效"),
    VALID("VALID", 2, "有效"), ALREADY_SEND_CMD("ALREADY_SEND_CMD", 3, "已发送扣款指令");

    private String code;
    private Integer flag;
    private String desc;

    EPayFlag(String code, Integer flag, String desc) {
        this.code = code;
        this.flag = flag;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
