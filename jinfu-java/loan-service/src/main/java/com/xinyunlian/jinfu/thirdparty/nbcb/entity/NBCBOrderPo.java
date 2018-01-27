package com.xinyunlian.jinfu.thirdparty.nbcb.entity;

import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBApprStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBCreditStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBLoanStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBReceiveStatus;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.converter.ENBCBApprStatusEnumConverter;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.converter.ENBCBCreditStatusEnumConverter;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.converter.ENBCBLoanStatusEnumConverter;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.converter.ENBCBReceiveStatusEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by bright on 2017/5/15.
 */
@Entity
@Table(name = "EXTERNAL_NBCB_LOAN")
@EntityListeners(IdInjectionEntityListener.class)
public class NBCBOrderPo {

    @Id
    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "RECEIVE_STATUS")
    @Convert(converter = ENBCBReceiveStatusEnumConverter.class)
    private ENBCBReceiveStatus receiveStatus;

    @Column(name = "APPR_STATUS")
    @Convert(converter = ENBCBApprStatusEnumConverter.class)
    private ENBCBApprStatus apprStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREDIT_DEAD_LINE")
    private Date creditDeadLine;

    @Column(name = "CREDIT_STATUS")
    @Convert(converter = ENBCBCreditStatusEnumConverter.class)
    private ENBCBCreditStatus creditStatus;

    @Column(name = "CREDIT")
    private BigDecimal credit;

    @Column(name = "LOAN_REMAINING")
    private BigDecimal loanRemaining;

    @Column(name = "LOAN_REMAINING_AVG")
    private BigDecimal loanRemainingAvg;

    @Column(name = "LOAN_STATUS")
    @Convert(converter = ENBCBLoanStatusEnumConverter.class)
    private ENBCBLoanStatus loanStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFY_TS")
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
