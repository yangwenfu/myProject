package com.xinyunlian.jinfu.repay.dto.resp;

import java.io.Serializable;

/**
 * Created by Willwang on 2017/4/11.
 */
public class LoanCashierCallbackDto implements Serializable{

    private Boolean success;

    private String desc;

    private String retCode;

    private String retMsg;

    private String passbackParams;

    private String retChannelCode;

    private String retChannelName;

    public String getRetChannelCode() {
        return retChannelCode;
    }

    public void setRetChannelCode(String retChannelCode) {
        this.retChannelCode = retChannelCode;
    }

    public String getRetChannelName() {
        return retChannelName;
    }

    public void setRetChannelName(String retChannelName) {
        this.retChannelName = retChannelName;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getPassbackParams() {
        return passbackParams;
    }

    public void setPassbackParams(String passbackParams) {
        this.passbackParams = passbackParams;
    }
}
