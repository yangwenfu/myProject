package com.xinyunlian.jinfu.balance.dto;

import com.xinyunlian.jinfu.balance.enums.EBalanceStatus;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Willwang on 2017/5/23.
 */
public class BalanceDetailListLoanDto implements Serializable{

    private Long detailId;

    private EBalanceStatus balanceStatus;

    private String repayDate;

    private String repayId;

    private BigDecimal repayAmt;

    private ERepayStatus repayStatus;

    private String channelName;

    private String balanceDate;

    private String userId;

    private String userName;

    private String loanId;

    private String prodName;

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public EBalanceStatus getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(EBalanceStatus balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

    public BigDecimal getRepayAmt() {
        return repayAmt;
    }

    public void setRepayAmt(BigDecimal repayAmt) {
        this.repayAmt = repayAmt;
    }

    public ERepayStatus getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(ERepayStatus repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(String balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
}
