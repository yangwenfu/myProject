package com.xinyunlian.jinfu.zrfundstx.dto;

/**
 * Created by dongfangchao on 2017/1/3/0003.
 */
public class SuperCashTradeQueryReq extends BaseReq {

    private static final long serialVersionUID = -647107401634970035L;

    private String TransactionAccountID;

    private String ApplicationNo;

    private String AppSheetSerialNo;

    private Long BeginDate;

    private Long EndDate;

    private Integer PageNo;

    private Integer PageSize;

    public String getTransactionAccountID() {
        return TransactionAccountID;
    }

    public void setTransactionAccountID(String transactionAccountID) {
        TransactionAccountID = transactionAccountID;
    }

    public String getApplicationNo() {
        return ApplicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        ApplicationNo = applicationNo;
    }

    public String getAppSheetSerialNo() {
        return AppSheetSerialNo;
    }

    public void setAppSheetSerialNo(String appSheetSerialNo) {
        AppSheetSerialNo = appSheetSerialNo;
    }

    public Long getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(Long beginDate) {
        BeginDate = beginDate;
    }

    public Long getEndDate() {
        return EndDate;
    }

    public void setEndDate(Long endDate) {
        EndDate = endDate;
    }

    public Integer getPageNo() {
        return PageNo;
    }

    public void setPageNo(Integer pageNo) {
        PageNo = pageNo;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }
}
