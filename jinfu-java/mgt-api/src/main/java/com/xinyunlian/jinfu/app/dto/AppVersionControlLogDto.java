package com.xinyunlian.jinfu.app.dto;

import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/7/5/0005.
 */
public class AppVersionControlLogDto implements Serializable {
    private static final long serialVersionUID = -4862666424601667729L;

    private Long appLogId;

    private String versionName;

    private Long versionCode;

    private String appPath;

    private Boolean appForceUpdate;

    private EAppSource appSource;

    private EOperatingSystem operatingSystem;

    private String updateTip;

    private Date releaseTime;

    public Long getAppLogId() {
        return appLogId;
    }

    public void setAppLogId(Long appLogId) {
        this.appLogId = appLogId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Long versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public Boolean getAppForceUpdate() {
        return appForceUpdate;
    }

    public void setAppForceUpdate(Boolean appForceUpdate) {
        this.appForceUpdate = appForceUpdate;
    }

    public EAppSource getAppSource() {
        return appSource;
    }

    public void setAppSource(EAppSource appSource) {
        this.appSource = appSource;
    }

    public EOperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(EOperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getUpdateTip() {
        return updateTip;
    }

    public void setUpdateTip(String updateTip) {
        this.updateTip = updateTip;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }
}
