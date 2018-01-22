package com.xinyunlian.jinfu.report.loan.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.report.loan.enums.EScheduleStatus;

/**
 * Created by dell on 2016/11/7.
 */
public class RepaySchdSearchDto extends PagingDto<RepayScheduleDto> {
    private static final long serialVersionUID = -1L;

    private String dueDateStart;

    private String dueDateEnd;

    private String loanId;

    private EScheduleStatus repayStatus;

    private String productName;

    public String getDueDateStart() {
        return dueDateStart;
    }

    public void setDueDateStart(String dueDateStart) {
        this.dueDateStart = dueDateStart;
    }

    public String getDueDateEnd() {
        return dueDateEnd;
    }

    public void setDueDateEnd(String dueDateEnd) {
        this.dueDateEnd = dueDateEnd;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public EScheduleStatus getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(EScheduleStatus repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
