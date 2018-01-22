package com.xinyunlian.jinfu.loan.dto.user;

import java.io.Serializable;

/**
 * Created by dell1 on 2016/9/27.
 */
public class LoanCUserCheckDto implements Serializable {
    private boolean baseIsInvalid;
    private boolean linkmanIsInvalid;
    private boolean storeIsInvalid;
    private boolean isNotAuth;

    public boolean isBaseIsInvalid() {
        return baseIsInvalid;
    }

    public void setBaseIsInvalid(boolean baseIsInvalid) {
        this.baseIsInvalid = baseIsInvalid;
    }

    public boolean isLinkmanIsInvalid() {
        return linkmanIsInvalid;
    }

    public void setLinkmanIsInvalid(boolean linkmanIsInvalid) {
        this.linkmanIsInvalid = linkmanIsInvalid;
    }

    public boolean isStoreIsInvalid() {
        return storeIsInvalid;
    }

    public void setStoreIsInvalid(boolean storeIsInvalid) {
        this.storeIsInvalid = storeIsInvalid;
    }

    public boolean isNotAuth() {
        return isNotAuth;
    }

    public void setNotAuth(boolean notAuth) {
        isNotAuth = notAuth;
    }
}
