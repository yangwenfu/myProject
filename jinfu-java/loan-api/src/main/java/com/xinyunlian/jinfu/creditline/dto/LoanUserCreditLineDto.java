package com.xinyunlian.jinfu.creditline.dto;

import com.xinyunlian.jinfu.creditline.enums.ELoanUserCreditLineStatus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 */
public class LoanUserCreditLineDto implements Serializable{

    /**
     * 授信额度
     */
    private BigDecimal total;

    /**
     * 可用额度
     */
    private BigDecimal available;

    /**
     * 额度获取状态
     */
    private ELoanUserCreditLineStatus status;

    /**
     * 本期应还
     */
    private BigDecimal should;

    /**
     * 到期还款日
     */
    private String dueDate;

    public ELoanUserCreditLineStatus getStatus() {
        return status;
    }

    public void setStatus(ELoanUserCreditLineStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total != null ? total : BigDecimal.ZERO;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAvailable() {
        return available != null ? available : BigDecimal.ZERO;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public BigDecimal getShould() {
        return should;
    }

    public void setShould(BigDecimal should) {
        this.should = should;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
