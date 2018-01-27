package com.xinyunlian.jinfu.dealer.enums;

/**
 * Created by menglei on 2016年08月26日.
 */
public enum EDealerStatsOrderType {
    //代办订单显示内容分类（app用）
    USERNAME(1, "姓名"),
    STORENAME(2, "店铺名"),
    ALL(3, "全部");

    private Integer code;

    private String text;

    EDealerStatsOrderType(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
