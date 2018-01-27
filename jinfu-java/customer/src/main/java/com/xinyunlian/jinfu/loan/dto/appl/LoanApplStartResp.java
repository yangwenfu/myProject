package com.xinyunlian.jinfu.loan.dto.appl;

import java.io.Serializable;

/**
 * Created by Willwang on 2017/2/6.
 */
public class LoanApplStartResp implements Serializable {

    /**
     * 申请编号
     */
    private String applId;

    /**
     * 用户信息是否完善
     */
    @Deprecated
    private Boolean isPerfectInfo;

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public Boolean getPerfectInfo() {
        return isPerfectInfo;
    }

    public void setPerfectInfo(Boolean perfectInfo) {
        isPerfectInfo = perfectInfo;
    }
}
