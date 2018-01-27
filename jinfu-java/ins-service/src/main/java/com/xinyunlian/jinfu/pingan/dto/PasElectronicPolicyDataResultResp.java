package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/11/0011.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasElectronicPolicyDataResultResp implements Serializable {
    private static final long serialVersionUID = 4900245045596148142L;

    @JsonProperty("policyValue")
    private String policyValue;

    public String getPolicyValue() {
        return policyValue;
    }

    public void setPolicyValue(String policyValue) {
        this.policyValue = policyValue;
    }
}
