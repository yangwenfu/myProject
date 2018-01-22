package com.xinyunlian.jinfu.report.dealer.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年08月31日.
 */
public class DealerStatsMonthStoreDto implements Serializable {

    private String monthDate;
    private Long qrCodeCount;
    private Long registerCount;
    private Long noteCount;

    public String getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(String monthDate) {
        this.monthDate = monthDate;
    }

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

    public Long getNoteCount() {
        return noteCount;
    }

    public void setNoteCount(Long noteCount) {
        this.noteCount = noteCount;
    }
}
