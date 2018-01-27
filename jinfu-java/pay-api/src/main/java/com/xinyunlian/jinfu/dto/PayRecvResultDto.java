package com.xinyunlian.jinfu.dto;

import com.xinyunlian.jinfu.enums.PayRecvResult;

import java.io.Serializable;

/**
 * Created by jl062 on 2016/12/16.
 */
public class PayRecvResultDto implements Serializable{

    private PayRecvResult payRecvResult;

    private String retCode;

    private String retMsg;

    public String getRetCode() {
        return retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public PayRecvResult getPayRecvResult() {
        return payRecvResult;
    }

    public PayRecvResultDto(PayRecvResult payRecvResult, String retCode, String retMsg) {
        this.payRecvResult = payRecvResult;
        this.retCode = retCode;
        this.retMsg = retMsg;
    }
}
