package com.xinyunlian.jinfu.api.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017/01/04.
 */

public class ApiNotifyDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String orderNo;

    private String respCode;

    private String respDesc;

    private String signature;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
