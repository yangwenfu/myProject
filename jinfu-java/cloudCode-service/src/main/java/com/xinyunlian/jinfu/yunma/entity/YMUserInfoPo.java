package com.xinyunlian.jinfu.yunma.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;

import javax.persistence.*;

/**
 * Created by menglei on 2017年01月04日.
 */
@Entity
@Table(name = "ym_userinfo")
@EntityListeners(IdInjectionEntityListener.class)
public class YMUserInfoPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "YM_USER_ID")
    private String ymUserId;

    @Column(name = "OPEN_ID")
    private String openId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "MOBILE")
    private String mobile;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getYmUserId() {
        return ymUserId;
    }

    public void setYmUserId(String ymUserId) {
        this.ymUserId = ymUserId;
    }
}
