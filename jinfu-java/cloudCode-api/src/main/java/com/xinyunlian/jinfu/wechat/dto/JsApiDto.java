package com.xinyunlian.jinfu.wechat.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年01月04日.
 */
public class JsApiDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private String appId;// 必填，公众号的唯一标识

    private String timestamp;// 必填，生成签名的时间戳

    private String nonceStr;// 必填，生成签名的随机串

    private String signature;// 必填，签名

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
