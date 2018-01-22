package com.xinyunlian.jinfu.yunma.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年01月04日.
 */
public class YmThirdConfigDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long id;
    private String name;
    private String appId;
    private String key;
    private String bindNotifyUrl;
    private String orderNotifyUrl;
    private String notifyUrl;

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

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getBindNotifyUrl() {
        return bindNotifyUrl;
    }

    public void setBindNotifyUrl(String bindNotifyUrl) {
        this.bindNotifyUrl = bindNotifyUrl;
    }

    public String getOrderNotifyUrl() {
        return orderNotifyUrl;
    }

    public void setOrderNotifyUrl(String orderNotifyUrl) {
        this.orderNotifyUrl = orderNotifyUrl;
    }
}
