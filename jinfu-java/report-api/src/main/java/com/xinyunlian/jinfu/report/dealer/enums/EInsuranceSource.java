package com.xinyunlian.jinfu.report.dealer.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by DongFC on 2016-09-21.
 */
public enum EInsuranceSource implements PageEnum{

    BUDDY("BUDDY","小伙伴"), SMALLLOANAPP("SMALLLOANAPP","小贷APP"), MALL("MALL", "商城"), EORDER("EORDER", "易订单");

    private String code;
    private String text;

    EInsuranceSource() {
    }

    EInsuranceSource(String code, String text) {
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
