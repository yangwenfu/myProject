package com.xinyunlian.jinfu.gather.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/3/15/0015.
 */
public class AppDownloadGaDto implements Serializable {
    private static final long serialVersionUID = 5500664801669777080L;

    private String deviceId;

    private String gatherType;

    private String source;

    private String appName;

    private String appVersion;

    private String gatherTs;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGatherType() {
        return gatherType;
    }

    public void setGatherType(String gatherType) {
        this.gatherType = gatherType;
    }

    public String getGatherTs() {
        return gatherTs;
    }

    public void setGatherTs(String gatherTs) {
        this.gatherTs = gatherTs;
    }
}
