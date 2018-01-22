package com.xinyunlian.jinfu.zrfundstx.dto;

/**
 * Created by dongfangchao on 2016/11/29/0029.
 */
public class SuperCashQueryShareReq extends BaseReq {
    private static final long serialVersionUID = 963002181762309166L;

    private String transactionAccountID;

    private String applicationNo;

    private String fundCode;

    private String custType;

    private Long pageNo;

    private Long pageSize;

    public String getTransactionAccountID() {
        return transactionAccountID;
    }

    public void setTransactionAccountID(String transactionAccountID) {
        this.transactionAccountID = transactionAccountID;
    }

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

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
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
