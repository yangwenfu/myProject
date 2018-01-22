package com.xinyunlian.jinfu.loan.dto.req;

import com.xinyunlian.jinfu.appl.enums.ELoanApplSortType;
import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanSearchResultDto;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.schedule.enums.ELoanCustomerStatus;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by JL on 2016/9/28.
 */
public class LoanSearchListDto extends PagingDto<LoanSearchResultDto> {
    private String applId;

    private String loanId;

    private String loanName;

    private String userName;

    private String loanStartDate;

    private String loanEndDate;

    private String transferStart;

    private String transferEnd;

    private ELoanCustomerStatus status;

    private Set<String> userIds = new HashSet<>();

    private String prodId;

    private ETransferStat transferStat;

    private ELoanStat loanStat;

    private ELoanApplSortType sortType;

    /**
     * 资金来源
     */
    private EFinanceSourceType financeSourceType;

    public EFinanceSourceType getFinanceSourceType() {
        return financeSourceType;
    }

    public void setFinanceSourceType(EFinanceSourceType financeSourceType) {
        this.financeSourceType = financeSourceType;
    }

    public String getTransferStart() {
        return transferStart;
    }

    public void setTransferStart(String transferStart) {
        this.transferStart = transferStart;
    }

    public ELoanStat getLoanStat() {
        return loanStat;
    }

    public void setLoanStat(ELoanStat loanStat) {
        this.loanStat = loanStat;
    }

    /**
     * 烟草许可证
     */
    private String tobacco;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getTobacco() {
        return tobacco;
    }

    public void setTobacco(String tobacco) {
        this.tobacco = tobacco;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(String loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public String getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(String loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    public String getTransferEnd() {
        return transferEnd;
    }

    public void setTransferEnd(String transferEnd) {
        this.transferEnd = transferEnd;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public ELoanCustomerStatus getStatus() {
        return status;
    }

    public void setStatus(ELoanCustomerStatus status) {
        this.status = status;
    }

    public ETransferStat getTransferStat() {
        return transferStat;
    }

    public void setTransferStat(ETransferStat transferStat) {
        this.transferStat = transferStat;
    }

    public ELoanApplSortType getSortType() {
        return sortType;
    }

    public void setSortType(ELoanApplSortType sortType) {
        this.sortType = sortType;
    }
}
