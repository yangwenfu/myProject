package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
public class QunarApplyContExtInfoReq implements Serializable {
    private static final long serialVersionUID = 7234207911617795467L;

    @JsonProperty("isPolicyBeforePayfee")
    private String isPolicyBeforePayfee;

    public String getIsPolicyBeforePayfee() {
        return isPolicyBeforePayfee;
    }

    public void setIsPolicyBeforePayfee(String isPolicyBeforePayfee) {
        this.isPolicyBeforePayfee = isPolicyBeforePayfee;
    }
}
