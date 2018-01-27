package com.xinyunlian.jinfu.appl.dto;

import java.io.Serializable;

/**
 * Created by Willwang on 2017/5/3.
 */
public class LoanApplMqDto implements Serializable {

    private String applId;

    private String userId;

    private String traceId;

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
