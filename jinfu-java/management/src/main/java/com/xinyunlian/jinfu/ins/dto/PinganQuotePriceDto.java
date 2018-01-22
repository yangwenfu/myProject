package com.xinyunlian.jinfu.ins.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public class PinganQuotePriceDto implements Serializable {
    private static final long serialVersionUID = 4842632486382606471L;

    @NotNull(message = "区或县的id不能为空")
    private Long countyId;

    @NotBlank(message = "保额档次编号不能为空")
    private String insuredGradeCode;

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getInsuredGradeCode() {
        return insuredGradeCode;
    }

    public void setInsuredGradeCode(String insuredGradeCode) {
        this.insuredGradeCode = insuredGradeCode;
    }
}
