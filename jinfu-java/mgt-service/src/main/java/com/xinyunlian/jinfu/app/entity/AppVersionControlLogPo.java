package com.xinyunlian.jinfu.app.entity;

import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;
import com.xinyunlian.jinfu.app.enums.converter.EAppSourceConverter;
import com.xinyunlian.jinfu.app.enums.converter.EOperatingSystemConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by DongFC on 2016-10-08.
 */
@Entity
@Table(name = "app_version_control_log")
public class AppVersionControlLogPo extends BaseMaintainablePo {

    private static final long serialVersionUID = -3440968285805597764L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APP_LOG_ID")
    private Long appLogId;

    @Column(name = "VERSION_NAME")
    private String versionName;

    @Column(name = "VERSION_CODE")
    private Long versionCode;

    @Column(name = "APP_PATH")
    private String appPath;

    @Column(name = "APP_FORCE_UPDATE")
    private Boolean appForceUpdate;

    @Column(name = "APP_SOURCE")
    @Convert(converter = EAppSourceConverter.class)
    private EAppSource appSource;

    @Column(name = "OPERATING_SYSTEM")
    @Convert(converter = EOperatingSystemConverter.class)
    private EOperatingSystem operatingSystem;

    @Column(name = "UPDATE_TIP")
    private String updateTip;

    @Column(name = "RELEASE_TIME")
    private Date releaseTime;

    public Long getAppLogId() {
        return appLogId;
    }

    public void setAppLogId(Long appLogId) {
        this.appLogId = appLogId;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public EAppSource getAppSource() {
        return appSource;
    }

    public void setAppSource(EAppSource appSource) {
        this.appSource = appSource;
    }

    public Boolean getAppForceUpdate() {
        return appForceUpdate;
    }

    public void setAppForceUpdate(Boolean appForceUpdate) {
        this.appForceUpdate = appForceUpdate;
    }

    public Long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Long versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
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
