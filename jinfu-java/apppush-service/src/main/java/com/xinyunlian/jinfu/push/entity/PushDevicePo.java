package com.xinyunlian.jinfu.push.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by apple on 2017/1/12.
 */
@Entity
@Table(name = "PUSH_DEVICE")
public class PushDevicePo extends BaseMaintainablePo {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PUSH_TOKEN")
    private String pushToken;


    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "APP_TYPE")
    private int appType;

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }
}
