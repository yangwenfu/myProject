package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotePriceResponse implements Serializable {
    private static final long serialVersionUID = 7228604384585550412L;

    @JsonProperty("responseCode")
    private String responseCode;
    @JsonProperty("responseMsg")
    private String responseMsg;
    @JsonProperty("result")
    private QuotePriceDataResultResponse result;

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

    public QuotePriceDataResultResponse getResult() {
        return result;
    }

    public void setResult(QuotePriceDataResultResponse result) {
        this.result = result;
    }
}
