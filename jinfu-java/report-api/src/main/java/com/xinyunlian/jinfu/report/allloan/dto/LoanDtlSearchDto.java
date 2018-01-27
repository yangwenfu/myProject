package com.xinyunlian.jinfu.report.allloan.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.report.allloan.enums.ELoanStat;

/**
 * Created by dell on 2016/11/7.
 */
public class LoanDtlSearchDto extends PagingDto<LoanDtlDto> {
    private static final long serialVersionUID = -1L;

    private String transferStartDate;

    private String transferEndDate;

    private String loanId;

    private ELoanStat loanStatus;

    private String productName;

    public String getTransferStartDate() {
        return transferStartDate;
    }

    public void setTransferStartDate(String transferStartDate) {
        this.transferStartDate = transferStartDate;
    }

    public String getTransferEndDate() {
        return transferEndDate;
    }

    public void setTransferEndDate(String transferEndDate) {
        this.transferEndDate = transferEndDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public ELoanStat getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(ELoanStat loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
