package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
public class QunarApplyContRiskGroupInfoReq implements Serializable {
    private static final long serialVersionUID = -4271500117716499940L;

    @JsonProperty("planInfoList")
    private List<QunarApplyContRiskGroupInfoPlanReq> planInfoList;

    public List<QunarApplyContRiskGroupInfoPlanReq> getPlanInfoList() {
        return planInfoList;
    }

    public void setPlanInfoList(List<QunarApplyContRiskGroupInfoPlanReq> planInfoList) {
        this.planInfoList = planInfoList;
    }
}
