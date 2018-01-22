package com.xinyunlian.jinfu.loan.dto.jms;

/**
 * Created by King on 2017/9/6.
 */
public enum LoanResult {
    REFUSE("建议拒绝"), PASS("建议通过"), REVIEW("人工审核");

    private String text;

    LoanResult(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
