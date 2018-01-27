package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
public class QunarApplyContReq implements Serializable {
    private static final long serialVersionUID = 1843855524518434691L;

    @JsonProperty("insurantInfoList")
    private List<QunarApplyContInsInfoReq> insurantInfoList;

    @JsonProperty("baseInfo")
    private QunarApplyContBaseInfoReq baseInfo;

    @JsonProperty("extendInfo")
    private QunarApplyContExtInfoReq extendInfo;

    @JsonProperty("riskAddressInfoList")
    private List<QunarApplyContRiskAddressInfoReq> riskAddressInfoList;

    @JsonProperty("riskGroupInfoList")
    private List<QunarApplyContRiskGroupInfoReq> riskGroupInfoList;

    public List<QunarApplyContInsInfoReq> getInsurantInfoList() {
        return insurantInfoList;
    }

    public void setInsurantInfoList(List<QunarApplyContInsInfoReq> insurantInfoList) {
        this.insurantInfoList = insurantInfoList;
    }

    public QunarApplyContBaseInfoReq getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(QunarApplyContBaseInfoReq baseInfo) {
        this.baseInfo = baseInfo;
    }

    public QunarApplyContExtInfoReq getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(QunarApplyContExtInfoReq extendInfo) {
        this.extendInfo = extendInfo;
    }

    public List<QunarApplyContRiskAddressInfoReq> getRiskAddressInfoList() {
        return riskAddressInfoList;
    }

    public void setRiskAddressInfoList(List<QunarApplyContRiskAddressInfoReq> riskAddressInfoList) {
        this.riskAddressInfoList = riskAddressInfoList;
    }

    public List<QunarApplyContRiskGroupInfoReq> getRiskGroupInfoList() {
        return riskGroupInfoList;
    }

    public void setRiskGroupInfoList(List<QunarApplyContRiskGroupInfoReq> riskGroupInfoList) {
        this.riskGroupInfoList = riskGroupInfoList;
    }
}
