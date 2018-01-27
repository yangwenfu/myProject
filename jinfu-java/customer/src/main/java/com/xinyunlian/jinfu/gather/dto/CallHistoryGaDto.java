package com.xinyunlian.jinfu.gather.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/3/15/0015.
 */
public class CallHistoryGaDto implements Serializable {
    private static final long serialVersionUID = 338379424818138515L;

    private String id;

    private String contractName;

    private String phone;

    private Boolean call;

    private String startTs;

    private String duration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getCall() {
        return call;
    }

    public void setCall(Boolean call) {
        this.call = call;
    }

    public String getStartTs() {
        return startTs;
    }

    public void setStartTs(String startTs) {
        this.startTs = startTs;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
