package com.xinyunlian.jinfu.zrfundstx.dto;

/**
 * Created by dongfangchao on 2016/11/30/0030.
 */
public class SuperCashQnReq extends BaseReq {
    private static final long serialVersionUID = 6200092743312424031L;

    private String applicationNo;

    private String fundCode;

    private Long beginDate;

    private Long endDate;

    private Long pageNo;

    private Long pageSize;

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public Long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
}
