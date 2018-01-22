package com.xinyunlian.jinfu.pingan.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public class PinganInsuredGradeDto implements Serializable {
    private static final long serialVersionUID = -2095661931700720847L;

    private Long id;

    private String insuredGradeCode;

    private String insuredAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInsuredGradeCode() {
        return insuredGradeCode;
    }

    public void setInsuredGradeCode(String insuredGradeCode) {
        this.insuredGradeCode = insuredGradeCode;
    }

    public String getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(String insuredAmount) {
        this.insuredAmount = insuredAmount;
    }
}
