package com.xinyunlian.jinfu.stats.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年09月21日.
 */
public class StatsOrderDto implements Serializable {

    private String prodId;

    private String prodName;

    private Long successCount;

    private Long allCount;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Long successCount) {
        this.successCount = successCount;
    }

    public Long getAllCount() {
        return allCount;
    }

    public void setAllCount(Long allCount) {
        this.allCount = allCount;
    }
}
