package com.xinyunlian.jinfu.report.dealer.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年08月31日.
 */
public class DealerStatsMonthDto implements Serializable {

    private String monthDate;
    private String userId;
    private String dealerId;
    private Long registerCount;
    private Long qrCodeCount;
    private Long noteCount;

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

    public Long getRegisterCount() {
        return registerCount;
    }

    public void setRegisterCount(Long registerCount) {
        this.registerCount = registerCount;
    }

    public Long getQrCodeCount() {
        return qrCodeCount;
    }

    public void setQrCodeCount(Long qrCodeCount) {
        this.qrCodeCount = qrCodeCount;
    }

    public Long getNoteCount() {
        return noteCount;
    }

    public void setNoteCount(Long noteCount) {
        this.noteCount = noteCount;
    }

    public String getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(String monthDate) {
        this.monthDate = monthDate;
    }
}
