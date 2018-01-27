package com.xinyunlian.jinfu.report.dealer.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年06月08日.
 */
public class DealerStatsMonthObjectDto implements Serializable {

    private String monthDate;
    private String userId;
    private String dealerId;
    private Long count;

    public String getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(String monthDate) {
        this.monthDate = monthDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
