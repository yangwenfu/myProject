package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/14/0014.
 */
public class PasElectronicPolicyContBaseInfoReq implements Serializable {
    private static final long serialVersionUID = 7895371972671230525L;

    @JsonProperty("applyPolicyNo")
    private String applyPolicyNo;
    @JsonProperty("policyNo")
    private String policyNo;

    public String getApplyPolicyNo() {
        return applyPolicyNo;
    }

    public void setApplyPolicyNo(String applyPolicyNo) {
        this.applyPolicyNo = applyPolicyNo;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }
}
