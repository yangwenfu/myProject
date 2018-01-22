package com.xinyunlian.jinfu.balance.dto;

import com.xinyunlian.jinfu.balance.enums.EBalanceOutlineStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Willwang on 2017/5/19.
 */
public class BalanceOutlineDto implements Serializable{

    private Long id;

    private Date generateDate;

    private String generateDateStr;

    private Integer repayCount;

    private BigDecimal repayAmt;

    private EBalanceOutlineStatus balanceOutlineStatus;

    private Date balanceDate;

    private String balanceDateStr;

    private String balanceUserId;

    private String balanceUserName;

    private String partnerId;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getBalanceDateStr() {
        return balanceDateStr;
    }

    public void setBalanceDateStr(String balanceDateStr) {
        this.balanceDateStr = balanceDateStr;
    }

    public String getGenerateDateStr() {
        return generateDateStr;
    }

    public void setGenerateDateStr(String generateDateStr) {
        this.generateDateStr = generateDateStr;
    }

    public String getBalanceUserName() {
        return balanceUserName;
    }

    public void setBalanceUserName(String balanceUserName) {
        this.balanceUserName = balanceUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

    public Integer getRepayCount() {
        return repayCount;
    }

    public void setRepayCount(Integer repayCount) {
        this.repayCount = repayCount;
    }

    public BigDecimal getRepayAmt() {
        return repayAmt;
    }

    public void setRepayAmt(BigDecimal repayAmt) {
        this.repayAmt = repayAmt;
    }

    public EBalanceOutlineStatus getBalanceOutlineStatus() {
        return balanceOutlineStatus;
    }

    public void setBalanceOutlineStatus(EBalanceOutlineStatus balanceOutlineStatus) {
        this.balanceOutlineStatus = balanceOutlineStatus;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getBalanceUserId() {
        return balanceUserId;
    }

    public void setBalanceUserId(String balanceUserId) {
        this.balanceUserId = balanceUserId;
    }
}
