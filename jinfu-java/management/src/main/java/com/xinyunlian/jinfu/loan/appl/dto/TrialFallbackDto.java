package com.xinyunlian.jinfu.loan.appl.dto;

import java.io.Serializable;

/**
 * Created by Willwang on 2017/2/20.
 */
public class TrialFallbackDto implements Serializable {

    private String applId;

    private String reason;

    private String remark;

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
