package com.xinyunlian.jinfu.loan.dto.resp;

import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.schedule.enums.ELoanCustomerStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by JL on 2016/9/28.
 */
public class LoanSearchResultDto implements Serializable{

    private String loanId;

    private String applId;

    //申请时间
    private String applDate;

    private String loanName;

    private String userId;

    private String loanDate;

    private BigDecimal loanAmt;

    private String dutDate;

    private String transferDate;

    private Integer period;

    private String unit;

    private BigDecimal rate;

    private ETermType termType;

    private ERepayMode repayMode;

    private ELoanStat loanStat;

    private ELoanCustomerStatus status;

    private ETransferStat transferStat;

    private String userName;

    private String mobile;

    /**
     * 结清日期
     */
    private String repayDate;

    private String prodId;

    /**
     * 剩余本金
     */
    private BigDecimal surplus;

    /**
     * 资金来源编号
     */
    private Integer financeSourceId;

    /**
     * 资金路由类型
     */
    private EFinanceSourceType financeSourceType;

    public EFinanceSourceType getFinanceSourceType() {
        return financeSourceType;
    }

    public void setFinanceSourceType(EFinanceSourceType financeSourceType) {
        this.financeSourceType = financeSourceType;
    }

    public Integer getFinanceSourceId() {
        return financeSourceId;
    }

    public void setFinanceSourceId(Integer financeSourceId) {
        this.financeSourceId = financeSourceId;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getSurplus() {
        return surplus;
    }

    public void setSurplus(BigDecimal surplus) {
        this.surplus = surplus;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public String getDutDate() {
        return dutDate;
    }

    public void setDutDate(String dutDate) {
        this.dutDate = dutDate;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public ETermType getTermType() {
        return termType;
    }

    public void setTermType(ETermType termType) {
        this.termType = termType;
    }

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
    }

    public ELoanStat getLoanStat() {
        return loanStat;
    }

    public void setLoanStat(ELoanStat loanStat) {
        this.loanStat = loanStat;
    }

    public ETransferStat getTransferStat() {
        return transferStat;
    }

    public void setTransferStat(ETransferStat transferStat) {
        this.transferStat = transferStat;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getApplDate() {
        return applDate;
    }

    public void setApplDate(String applDate) {
        this.applDate = applDate;
    }

    public ELoanCustomerStatus getStatus() {
        return status;
    }

    public void setStatus(ELoanCustomerStatus status) {
        this.status = status;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
