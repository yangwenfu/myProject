package com.xinyunlian.jinfu.yunma.dto;

/**
 * Created by menglei on 2017年08月29日.
 */
public class YmBatchSaveDto {

    private Long startNo;

    private Long endNo;

    private String remark;

    public Long getStartNo() {
        return startNo;
    }

    public void setStartNo(Long startNo) {
        this.startNo = startNo;
    }

    public Long getEndNo() {
        return endNo;
    }

    public void setEndNo(Long endNo) {
        this.endNo = endNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
