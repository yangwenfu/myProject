package com.xinyunlian.jinfu.app.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/5/0005.
 */
public class AppRollingBackDto implements Serializable {
    private static final long serialVersionUID = -2685944153046668143L;

    @NotBlank(message = "appSource can not be blank")
    private String appSource;

    @NotBlank(message = "operatingSystem can not be blank")
    private String operatingSystem;

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
}
