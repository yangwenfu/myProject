package com.xinyunlian.jinfu.customer.dto;

import java.io.Serializable;

/**
 * 已开通业务
 * Created by King on 2017/1/13.
 */
public class UserBusHasDto implements Serializable{
    private static final long serialVersionUID = 1L;
    private boolean hasYuMa;
    private boolean hasStoreIns;

    public boolean isHasYuMa() {
        return hasYuMa;
    }

    public void setHasYuMa(boolean hasYuMa) {
        this.hasYuMa = hasYuMa;
    }

    public boolean isHasStoreIns() {
        return hasStoreIns;
    }

    public void setHasStoreIns(boolean hasStoreIns) {
        this.hasStoreIns = hasStoreIns;
    }
}
