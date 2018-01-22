package com.xinyunlian.jinfu.thirdparty.nbcb.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by bright on 2017/5/15.
 */
public enum ENBCBApprStatus implements PageEnum {
    APPROVED("APPROVED", "审批通过额度生效"),
    PROCESSING("PROCESSING", "审批中"),
    REJECTED("REJECTED", "审批不通过");

    private String code;
    private String text;

    ENBCBApprStatus(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }
}
