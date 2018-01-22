package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/10/0010.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QunarApplyRespBase implements Serializable {
    private static final long serialVersionUID = 5412558997224963773L;

    @JsonProperty("ret")
    private String ret;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("data")
    private QunarApplyResponse data;

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

    public QunarApplyResponse getData() {
        return data;
    }

    public void setData(QunarApplyResponse data) {
        this.data = data;
    }
}
