package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/11/0011.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasElectronicPolicyDataResponse implements Serializable {
    private static final long serialVersionUID = 4596906935849885431L;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseMsg")
    private String responseMsg;

    @JsonProperty("result")
    private PasElectronicPolicyDataResultResp result;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public PasElectronicPolicyDataResultResp getResult() {
        return result;
    }

    public void setResult(PasElectronicPolicyDataResultResp result) {
        this.result = result;
    }
}
