package com.xinyunlian.jinfu.insurance.dto;

import java.io.Serializable;

/**
 * Created by DongFC on 2016-09-26.
 */
public class InsureCallBackRespDto implements Serializable{
    private static final long serialVersionUID = -3470492309937363518L;
    private String result;
    private String msg;
    private String sign_type;
    private String signature;

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getSign_type() {
        return sign_type;
    }
    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
}
