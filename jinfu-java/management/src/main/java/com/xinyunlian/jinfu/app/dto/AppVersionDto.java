package com.xinyunlian.jinfu.app.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/5/0005.
 */
public class AppVersionDto implements Serializable {
    private static final long serialVersionUID = 6042523229697402540L;

    @NotBlank(message = "appSource can not be blank")
    private String appSource;

    @NotBlank(message = "operatingSystem can not be blank")
    private String operatingSystem;

    @NotBlank(message = "versionName can not be blank")
    private String versionName;

    @NotBlank(message = "updateTip can not be blank")
    private String updateTip;

    public String getAppSource() {
        return appSource;
    }

    public void setAppSource(String appSource) {
        this.appSource = appSource;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateTip() {
        return updateTip;
    }

    public void setUpdateTip(String updateTip) {
        this.updateTip = updateTip;
    }
}
