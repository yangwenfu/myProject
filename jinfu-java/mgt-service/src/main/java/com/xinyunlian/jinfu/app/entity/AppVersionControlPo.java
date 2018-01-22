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
@Table(name = "app_version_control")
public class AppVersionControlPo extends BaseMaintainablePo {

    private static final long serialVersionUID = -3440968285805597764L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "NORMAL_RELEASE")
    private Boolean normalRelease;

    @Column(name = "RELEASE_TIME")
    private Date releaseTime;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
