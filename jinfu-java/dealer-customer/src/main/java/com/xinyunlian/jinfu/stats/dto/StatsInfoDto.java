package com.xinyunlian.jinfu.stats.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2016年09月21日.
 */
public class StatsInfoDto implements Serializable {

    private Long qrCodeCount;

    private Long registerCount;

    private Long signInfoCount;

    private String monthDate;

    private List<StatsOrderDto> statsOrders = new ArrayList<>();

    public Long getQrCodeCount() {
        return qrCodeCount;
    }

    public void setQrCodeCount(Long qrCodeCount) {
        this.qrCodeCount = qrCodeCount;
    }

    public Long getRegisterCount() {
        return registerCount;
    }

    public void setRegisterCount(Long registerCount) {
        this.registerCount = registerCount;
    }

    public Long getSignInfoCount() {
        return signInfoCount;
    }

    public void setSignInfoCount(Long signInfoCount) {
        this.signInfoCount = signInfoCount;
    }

    public List<StatsOrderDto> getStatsOrders() {
        return statsOrders;
    }

    public void setStatsOrders(List<StatsOrderDto> statsOrders) {
        this.statsOrders = statsOrders;
    }

    public String getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(String monthDate) {
        this.monthDate = monthDate;
    }

}
