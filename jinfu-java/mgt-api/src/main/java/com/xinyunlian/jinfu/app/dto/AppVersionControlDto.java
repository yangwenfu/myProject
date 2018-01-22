package com.xinyunlian.jinfu.app.dto;

import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DongFC on 2016-10-08.
 */
public class AppVersionControlDto implements Serializable {

    private static final long serialVersionUID = 7405368851495951454L;

    private Long id;

    private String versionName;

    private Long versionCode;

    private String appPath;

    private Boolean appForceUpdate;

    private EAppSource appSource;

    private EOperatingSystem operatingSystem;

    private String updateTip;

    private Boolean normalRelease;

    private Date releaseTime;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public EAppSource getAppSource() {
        return appSource;
    }

    public void setAppSource(EAppSource appSource) {
        this.appSource = appSource;
    }

    public Long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Long versionCode) {
        this.versionCode = versionCode;
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

    public Boolean getNormalRelease() {
        return normalRelease;
    }

    public void setNormalRelease(Boolean normalRelease) {
        this.normalRelease = normalRelease;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }
}
