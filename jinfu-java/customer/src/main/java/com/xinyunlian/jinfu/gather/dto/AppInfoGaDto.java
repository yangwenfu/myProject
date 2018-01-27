package com.xinyunlian.jinfu.gather.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/3/15/0015.
 */
public class AppInfoGaDto implements Serializable {
    private static final long serialVersionUID = -7133496010240497323L;

    private String appName;

    private String appVersion;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

}
