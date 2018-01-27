package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/16/0016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OauthTokenResponse implements Serializable {
    private static final long serialVersionUID = -1496476403369567633L;

    @JsonProperty("ret")
    private String ret;
    @JsonProperty("data")
    private OauthTokenDataResponse data;
    @JsonProperty("msg")
    private String msg;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public OauthTokenDataResponse getData() {
        return data;
    }

    public void setData(OauthTokenDataResponse data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
