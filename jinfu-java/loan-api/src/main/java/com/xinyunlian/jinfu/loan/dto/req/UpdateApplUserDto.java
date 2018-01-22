package com.xinyunlian.jinfu.loan.dto.req;

import java.io.Serializable;

/**
 * @author willwang
 */
public class UpdateApplUserDto implements Serializable{

    private String applId;

    private String userExtra;

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getUserExtra() {
        return userExtra;
    }

    public void setUserExtra(String userExtra) {
        this.userExtra = userExtra;
    }
}
