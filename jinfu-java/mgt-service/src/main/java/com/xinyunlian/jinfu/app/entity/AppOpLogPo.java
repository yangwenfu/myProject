package com.xinyunlian.jinfu.app.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dongfangchao on 2016/12/30/0030.
 */
@Entity
@Table(name = "app_op_log")
public class AppOpLogPo implements Serializable{

    private static final long serialVersionUID = 3209988507271254956L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "OPERATOR")
    private String operator;
    @Column(name = "OP_LOG")
    private String opLog;
    @Column(name = "APP_ID")
    private Long appId;
    @Column(name = "VERSION_NAME")
    private String versionName;
    @Column(name = "VERSION_CODE")
    private Long versionCode;
    @Column(name = "APP_FORCE_UPDATE")
    private Boolean appForceUpdate;
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOpLog() {
        return opLog;
    }

    public void setOpLog(String opLog) {
        this.opLog = opLog;
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

    public Boolean getAppForceUpdate() {
        return appForceUpdate;
    }

    public void setAppForceUpdate(Boolean appForceUpdate) {
        this.appForceUpdate = appForceUpdate;
    }
}
