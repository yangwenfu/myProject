package com.xinyunlian.jinfu.report.dealer.dto;

import com.xinyunlian.jinfu.report.dealer.enums.EDealerUserLogType;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/29.
 */
public class DealerLogSearchDto implements Serializable {
    private String name;

    private String mobile;

    private String dealerName;

    private EDealerUserLogType type;

    private String beginTime;

    private String endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public EDealerUserLogType getType() {
        return type;
    }

    public void setType(EDealerUserLogType type) {
        this.type = type;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
