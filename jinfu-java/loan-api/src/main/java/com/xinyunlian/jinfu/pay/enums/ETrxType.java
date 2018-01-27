package com.xinyunlian.jinfu.pay.enums;

/**
 * Created by cong on 2016/5/24.
 */
public enum ETrxType  {

    PAY_RECV("pay_recv", "代收代付"), PAY_RECV_RESULT_QUERY("result_query", "代收代付结果查询");

    private String code;

    private String text;

    ETrxType(String code, String text) {
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
