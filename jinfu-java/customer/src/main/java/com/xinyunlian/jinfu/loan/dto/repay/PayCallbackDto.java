package com.xinyunlian.jinfu.loan.dto.repay;

import java.io.Serializable;

/**
 * @author willwang
 */
public class PayCallbackDto implements Serializable {

    private String appid;

    private String encryptData;

    private String signData;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getEncryptData() {
        return encryptData;
    }

    public void setEncryptData(String encryptData) {
        this.encryptData = encryptData;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    @Override
    public String toString() {
        return "PayCallbackDto{" +
                "appid='" + appid + '\'' +
                ", encryptData='" + encryptData + '\'' +
                ", signData='" + signData + '\'' +
                '}';
    }
}
