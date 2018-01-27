package com.xinyunlian.jinfu.zrfundstx.enums;

/**
 * Created by dongfangchao on 2017/1/3/0003.
 */
public enum EConfirmFlag {

    CONFIRM_FAILURE("CONFIRM_FAILURE", 0, "确认失败"), CONFIRM_SUCCESS("CONFIRM_SUCCESS", 1, "确认成功"), PART_CONFIRM("PART_CONFIRM", 2, "部分确认"),
    REALTIME_CONFIRM_SUCCESS("REALTIME_CONFIRM_SUCCESS", 3, "实时确认成功"), CANCEL_TRADE("CANCEL_TRADE", 4, "已撤销交易"), APPLY_CONFIRM("APPLY_CONFIRM", 5, "认购行为确认"),
    NOT_CONFIRM("NOT_CONFIRM", 9, "未确认");

    private String code;
    private Integer flag;
    private String desc;

    EConfirmFlag(String code, Integer flag, String desc) {
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
