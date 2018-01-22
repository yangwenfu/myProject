package com.xinyunlian.jinfu.clerk.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by menglei on 2016-12-06.
 */
@Entity
@Table(name = "clerk_auth")
public class ClerkAuthPo extends BaseMaintainablePo {

    private static final long serialVersionUID = -6027402680599742591L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTH_ID")
    private Long authId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "STORE_ID")
    private String storeId;

    @Column(name = "CLERK_ID")
    private String clerkId;

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }
}
