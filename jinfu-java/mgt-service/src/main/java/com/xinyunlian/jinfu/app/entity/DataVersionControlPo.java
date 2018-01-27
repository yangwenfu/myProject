package com.xinyunlian.jinfu.app.entity;

import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.app.enums.EDataType;
import com.xinyunlian.jinfu.app.enums.EOperatingSystem;
import com.xinyunlian.jinfu.app.enums.converter.EAppSourceConverter;
import com.xinyunlian.jinfu.app.enums.converter.EDataTypeConverter;
import com.xinyunlian.jinfu.app.enums.converter.EOperatingSystemConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by menglei on 2016-12-14.
 */
@Entity
@Table(name = "data_version_control")
public class DataVersionControlPo extends BaseMaintainablePo {

    private static final long serialVersionUID = -3440968285805597764L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VERSION_NAME")
    private String versionName;

    @Column(name = "VERSION_CODE")
    private Long versionCode;

    @Column(name = "DATA_TYPE")
    @Convert(converter = EDataTypeConverter.class)
    private EDataType dataType;

    @Column(name = "DATA_PATH")
    private String dataPath;

    @Column(name = "DATA_FORCE_UPDATE")
    private Boolean dataForceUpdate;

    @Column(name = "APP_SOURCE")
    @Convert(converter = EAppSourceConverter.class)
    private EAppSource appSource;

    @Column(name = "OPERATING_SYSTEM")
    @Convert(converter = EOperatingSystemConverter.class)
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

    public EDataType getDataType() {
        return dataType;
    }

    public void setDataType(EDataType dataType) {
        this.dataType = dataType;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
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
