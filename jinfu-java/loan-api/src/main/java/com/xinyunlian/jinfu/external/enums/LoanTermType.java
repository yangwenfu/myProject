package com.xinyunlian.jinfu.external.enums;

import com.xinyunlian.jinfu.common.enums.PageEnum;

/**
 * Created by godslhand on 2017/6/20.
 */
public enum LoanTermType implements PageEnum {
    //1/7/14/28/30/60/90
    _1("1", "贷款1天"), _7("7", "贷款7天"),
    _14("14", "贷款14天"), _28("28", "贷款28天"),
    _30("30", "贷款30天"), _60("60", "贷款60天"),
    _90("90", "贷款90天"),
    ;

    private String code;
    private String text;

    LoanTermType(String code, String text) {
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
