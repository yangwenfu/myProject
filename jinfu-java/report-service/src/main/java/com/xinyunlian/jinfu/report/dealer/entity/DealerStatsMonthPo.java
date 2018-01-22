package com.xinyunlian.jinfu.report.dealer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by menglei on 2016/12/07.
 */
@Entity
@Table(name = "dealer_stats_month")
public class DealerStatsMonthPo implements Serializable {

    @Id
    @Column(name = "MONTH_DATE")
    private String monthDate;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "REGISTER_COUNT")
    private Long registerCount;

    @Column(name = "QR_CODE_COUNT")
    private Long qrCodeCount;

    @Column(name = "NOTE_COUNT")
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
