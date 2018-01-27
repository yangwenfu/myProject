package com.xinyunlian.jinfu.api.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/20/0020.
 */
public class ApiAppForceUpdateDto implements Serializable{

    private static final long serialVersionUID = -7287418838088549567L;
    private String appName;

    private String sourceType;

    private String version;

    private String sign;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
