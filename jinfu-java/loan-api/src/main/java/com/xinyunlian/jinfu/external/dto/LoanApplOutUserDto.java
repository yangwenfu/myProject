package com.xinyunlian.jinfu.external.dto;

import com.xinyunlian.jinfu.loan.enums.EApplOutType;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/25 0025.
 */
public class LoanApplOutUserDto implements Serializable {

    private String id;

    private String userId;

    private String outUserId;

    private EApplOutType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOutUserId() {
        return outUserId;
    }

    public void setOutUserId(String outUserId) {
        this.outUserId = outUserId;
    }

    public EApplOutType getType() {
        return type;
    }

    public void setType(EApplOutType type) {
        this.type = type;
    }
}
