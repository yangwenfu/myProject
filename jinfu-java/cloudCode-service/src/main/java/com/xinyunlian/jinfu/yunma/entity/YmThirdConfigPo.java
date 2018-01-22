package com.xinyunlian.jinfu.yunma.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by menglei on 2017年01月04日.
 */
@Entity
@Table(name = "ym_third_config")
public class YmThirdConfigPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "APP_ID")
    private String appId;

    @Column(name = "KEY")
    private String key;

    @Column(name = "BIND_NOTIFY_URL")
    private String bindNotifyUrl;

    @Column(name = "ORDER_NOTIFY_URL")
    private String orderNotifyUrl;

    @Column(name = "NOTIFY_URL")
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
