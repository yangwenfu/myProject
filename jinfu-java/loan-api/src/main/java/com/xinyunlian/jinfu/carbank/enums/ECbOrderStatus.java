package com.xinyunlian.jinfu.carbank.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public enum ECbOrderStatus implements PageEnum {
    SUBMIT_LOAN_WISH("1", "已提交贷款意向"), INVITE_TO_STORE("2", "已邀请到店办理手续"), SUCCESS_DEAL("3", "已成功办理"), ALREADY_CANCEL("4", "已取消订单");

    private String code;
    private String text;

    ECbOrderStatus(String code, String text) {
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
