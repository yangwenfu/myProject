package com.xinyunlian.jinfu.enums;

/**
 * Created by cong on 2016/5/29.
 */
public enum PayRecvResult {

    SUCCESS, FAILED, PROCESS;

    private String retCode;

    private String retMsg;

    public String getRetCode() {
        return retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    PayRecvResult()
    {
    }

    PayRecvResult(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public PayRecvResult setRetInfo(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        return this;
    }

}
