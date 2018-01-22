package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public class QuotePriceSubRequest implements Serializable {
    private static final long serialVersionUID = -169075150847860050L;

    @JsonProperty("isTyphoonControlArea")
    private String isTyphoonControlArea;

    @JsonProperty("insuredGradeCode")
    private String insuredGradeCode;

    public String getIsTyphoonControlArea() {
        return isTyphoonControlArea;
    }

    public void setIsTyphoonControlArea(String isTyphoonControlArea) {
        this.isTyphoonControlArea = isTyphoonControlArea;
    }

    public String getInsuredGradeCode() {
        return insuredGradeCode;
    }

    public void setInsuredGradeCode(String insuredGradeCode) {
        this.insuredGradeCode = insuredGradeCode;
    }
}
