package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QunarApplyResponse implements Serializable {
    private static final long serialVersionUID = -1921661186241078702L;

    @JsonProperty("responseCode")
    private String responseCode;
    @JsonProperty("responseMsg")
    private String responseMsg;
    @JsonProperty("result")
    private QunarApplyResultResponse result;

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

    public QunarApplyResultResponse getResult() {
        return result;
    }

    public void setResult(QunarApplyResultResponse result) {
        this.result = result;
    }
}
