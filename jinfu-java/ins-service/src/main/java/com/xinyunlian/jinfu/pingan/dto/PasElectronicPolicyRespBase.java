package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/11/0011.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasElectronicPolicyRespBase implements Serializable {
    private static final long serialVersionUID = -8467364274584950453L;

    @JsonProperty("ret")
    private String ret;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("data")
    private PasElectronicPolicyDataResponse data;

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

    public PasElectronicPolicyDataResponse getData() {
        return data;
    }

    public void setData(PasElectronicPolicyDataResponse data) {
        this.data = data;
    }
}
