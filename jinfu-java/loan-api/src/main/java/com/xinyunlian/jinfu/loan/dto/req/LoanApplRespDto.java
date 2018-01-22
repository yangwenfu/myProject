package com.xinyunlian.jinfu.loan.dto.req;

import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.schedule.enums.ELoanCustomerStatus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 * @date 2017-1-9 16:32:10
 */
public class LoanApplRespDto implements Serializable {

    private String applId;

    private String name;

    private BigDecimal amt;

    private String createDate;

    private ELoanCustomerStatus status;

    private ERepayMode repayMode;

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }


    public ELoanCustomerStatus getStatus() {
        return status;
    }

    public void setStatus(ELoanCustomerStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
    }
}
