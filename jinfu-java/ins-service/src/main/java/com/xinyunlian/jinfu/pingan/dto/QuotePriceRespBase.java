package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/10/0010.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotePriceRespBase implements Serializable {

    private static final long serialVersionUID = 2637705837438093234L;
    @JsonProperty("ret")
    private String ret;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("data")
    private QuotePriceResponse data;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public QuotePriceResponse getData() {
        return data;
    }

    public void setData(QuotePriceResponse data) {
        this.data = data;
    }
}
