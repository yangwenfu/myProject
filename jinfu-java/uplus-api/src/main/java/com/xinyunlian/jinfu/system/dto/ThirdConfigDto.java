package com.xinyunlian.jinfu.system.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年01月04日.
 */
public class ThirdConfigDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long id;
    private String name;
    private String appId;
    private String key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
