package com.xinyunlian.jinfu.dealer.dto;

import com.xinyunlian.jinfu.dealer.enums.EDealerOpLogType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2017年05月09日.
 */
public class DealerOpLogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String opeartor;

    private String opLog;

    private String dealerId;

    private EDealerOpLogType type;

    private String frontBase;

    private String afterBase;

    private Date createTs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpeartor() {
        return opeartor;
    }

    public void setOpeartor(String opeartor) {
        this.opeartor = opeartor;
    }

    public String getOpLog() {
        return opLog;
    }

    public void setOpLog(String opLog) {
        this.opLog = opLog;
    }

    public EDealerOpLogType getType() {
        return type;
    }

    public void setType(EDealerOpLogType type) {
        this.type = type;
    }

    public String getFrontBase() {
        return frontBase;
    }

    public void setFrontBase(String frontBase) {
        this.frontBase = frontBase;
    }

    public String getAfterBase() {
        return afterBase;
    }

    public void setAfterBase(String afterBase) {
        this.afterBase = afterBase;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }
}
