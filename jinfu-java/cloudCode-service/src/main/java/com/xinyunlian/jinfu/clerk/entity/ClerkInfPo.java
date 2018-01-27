package com.xinyunlian.jinfu.clerk.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;

import javax.persistence.*;

/**
 * Created by DongFC on 2016-11-03.
 */
@Entity
@Table(name = "clerk_inf")
@EntityListeners(IdInjectionEntityListener.class)
public class ClerkInfPo extends BaseMaintainablePo {

    private static final long serialVersionUID = -6027402680599742591L;

    @Id
    @Column(name = "CLERK_ID")
    private String clerkId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "OPEN_ID")
    private String openId;

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
