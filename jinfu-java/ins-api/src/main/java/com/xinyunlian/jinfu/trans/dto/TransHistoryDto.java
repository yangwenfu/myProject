package com.xinyunlian.jinfu.trans.dto;

import com.xinyunlian.jinfu.trans.enums.EInsTransType;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public class TransHistoryDto implements Serializable {
    private static final long serialVersionUID = 2587908058674152045L;

    private String transSerialNo;

    private String orderNo;

    private String requestUrl;

    private EInsTransType transType;

    private String transRequest;

    private String transResponse;

    public String getTransSerialNo() {
        return transSerialNo;
    }

    public void setTransSerialNo(String transSerialNo) {
        this.transSerialNo = transSerialNo;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public EInsTransType getTransType() {
        return transType;
    }

    public void setTransType(EInsTransType transType) {
        this.transType = transType;
    }

    public String getTransRequest() {
        return transRequest;
    }

    public void setTransRequest(String transRequest) {
        this.transRequest = transRequest;
    }

    public String getTransResponse() {
        return transResponse;
    }

    public void setTransResponse(String transResponse) {
        this.transResponse = transResponse;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
