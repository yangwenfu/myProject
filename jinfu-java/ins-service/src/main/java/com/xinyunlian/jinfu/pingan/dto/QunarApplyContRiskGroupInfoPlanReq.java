package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
public class QunarApplyContRiskGroupInfoPlanReq implements Serializable {
    private static final long serialVersionUID = -2546664803381044527L;

    @JsonProperty("planCode")
    private String planCode;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }
}
