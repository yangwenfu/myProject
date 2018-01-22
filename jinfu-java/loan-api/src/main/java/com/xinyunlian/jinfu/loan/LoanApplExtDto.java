package com.xinyunlian.jinfu.loan;

import java.io.Serializable;

/**
 * Created by Willwang on 2017/2/23.
 */
public class LoanApplExtDto implements Serializable {

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
