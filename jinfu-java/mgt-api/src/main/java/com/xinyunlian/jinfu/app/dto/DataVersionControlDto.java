package com.xinyunlian.jinfu.app.dto;

import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EDataType;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;

import java.io.Serializable;

/**
 * Created by menglei on 2016-12-14.
 */
public class DataVersionControlDto implements Serializable {

    private static final long serialVersionUID = 7405368851495951454L;

    private Long id;

    private String versionName;

    private Long versionCode;

    private String dataPath;

    private EDataType dataType;

    private Boolean dataForceUpdate;

    private EAppSource appSource;

    private EOperatingSystem operatingSystem;

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

    public Long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Long versionCode) {
        this.versionCode = versionCode;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public EDataType getDataType() {
        return dataType;
    }

    public void setDataType(EDataType dataType) {
        this.dataType = dataType;
    }

    public Boolean getDataForceUpdate() {
        return dataForceUpdate;
    }

    public void setDataForceUpdate(Boolean dataForceUpdate) {
        this.dataForceUpdate = dataForceUpdate;
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
}
