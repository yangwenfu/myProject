package com.xinyunlian.jinfu.app.dto;

import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/6/0006.
 */
public class AppVersionControlReq implements Serializable {
    private static final long serialVersionUID = 7715520108717951011L;

    @NotBlank(message = "versionName can not be blank")
    private String versionName;

    @NotNull(message = "versionCode can not be null")
    private Long versionCode;

    @NotNull(message = "appForceUpdate can not be null")
    private Boolean appForceUpdate;

    @NotNull(message = "appSource can not be null")
    private EAppSource appSource;

    @NotNull(message = "operatingSystem can not be null")
    private EOperatingSystem operatingSystem;

    @NotBlank(message = "updateTip can not be blank")
    private String updateTip;

    private String appPath;

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

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }
}
