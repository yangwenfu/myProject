package com.xinyunlian.jinfu.thirdparty.nbcb.dto;

import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBApprStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBCreditStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBLoanStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBReceiveStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by bright on 2017/5/15.
 */
public class NBCBOrderDto implements Serializable{
    private static final long serialVersionUID = -8930393072745281486L;

    private String orderNo;

    private String userId;

    private ENBCBReceiveStatus receiveStatus;

    private ENBCBApprStatus apprStatus;

    private Date creditDeadLine;

    private ENBCBCreditStatus creditStatus;

    private BigDecimal credit;

    private BigDecimal loanRemaining;

    private BigDecimal loanRemainingAvg;

    private ENBCBLoanStatus loanStatus;

    private Date modifyTs;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ENBCBReceiveStatus getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(ENBCBReceiveStatus receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public ENBCBApprStatus getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(ENBCBApprStatus apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Date getCreditDeadLine() {
        return creditDeadLine;
    }

    public void setCreditDeadLine(Date creditDeadLine) {
        this.creditDeadLine = creditDeadLine;
    }

    public ENBCBCreditStatus getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(ENBCBCreditStatus creditStatus) {
        this.creditStatus = creditStatus;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getLoanRemaining() {
        return loanRemaining;
    }

    public void setLoanRemaining(BigDecimal loanRemaining) {
        this.loanRemaining = loanRemaining;
    }

    public BigDecimal getLoanRemainingAvg() {
        return loanRemainingAvg;
    }

    public void setLoanRemainingAvg(BigDecimal loanRemainingAvg) {
        this.loanRemainingAvg = loanRemainingAvg;
    }

    public ENBCBLoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(ENBCBLoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public Date getModifyTs() {
        return modifyTs;
    }

    public void setModifyTs(Date modifyTs) {
        this.modifyTs = modifyTs;
    }
}
