package com.xinyunlian.jinfu.stats.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017年05月17日.
 */
public class StatsDetailDto implements Serializable {

    private List<StatsStoreDto> statsStores = new ArrayList<>();

    private List<StatsOrderDto> statsOrders = new ArrayList<>();

    private String beginTime;

    private String endTime;

    private String monthDate;

    public List<StatsStoreDto> getStatsStores() {
        return statsStores;
    }

    public void setStatsStores(List<StatsStoreDto> statsStores) {
        this.statsStores = statsStores;
    }

    public List<StatsOrderDto> getStatsOrders() {
        return statsOrders;
    }

    public void setStatsOrders(List<StatsOrderDto> statsOrders) {
        this.statsOrders = statsOrders;
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

    public String getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(String monthDate) {
        this.monthDate = monthDate;
    }
}
