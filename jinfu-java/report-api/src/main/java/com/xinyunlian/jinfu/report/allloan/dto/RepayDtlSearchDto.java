package com.xinyunlian.jinfu.report.allloan.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.report.allloan.enums.ETransMode;

/**
 * Created by dell on 2016/11/7.
 */
public class RepayDtlSearchDto extends PagingDto<RepayDtlDto> {

    private String repayStartDate;

    private String repayEndDate;

    private String loanId;

    private ETransMode transMode;

    private String productName;

    public String getRepayStartDate() {
        return repayStartDate;
    }

    public void setRepayStartDate(String repayStartDate) {
        this.repayStartDate = repayStartDate;
    }

    public String getRepayEndDate() {
        return repayEndDate;
    }

    public void setRepayEndDate(String repayEndDate) {
        this.repayEndDate = repayEndDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public ETransMode getTransMode() {
        return transMode;
    }

    public void setTransMode(ETransMode transMode) {
        this.transMode = transMode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
