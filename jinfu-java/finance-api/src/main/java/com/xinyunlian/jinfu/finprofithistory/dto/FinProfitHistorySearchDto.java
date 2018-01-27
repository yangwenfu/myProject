package com.xinyunlian.jinfu.finprofithistory.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2016/11/23.
 */
public class FinProfitHistorySearchDto extends PagingDto<FinProfitHistoryDto> {

    private static final long serialVersionUID = -469267920241080671L;

    private Long id;

    private String userId;

    private EFinOrg finOrg;

    private String extTxAccId;

    private Long finFundId;

    private BigDecimal profitAmt;

    private Date profitDate;

    private Date profitDateFrom;

    private Date profitDateTo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public EFinOrg getFinOrg() {
        return finOrg;
    }

    public void setFinOrg(EFinOrg finOrg) {
        this.finOrg = finOrg;
    }

    public String getExtTxAccId() {
        return extTxAccId;
    }

    public void setExtTxAccId(String extTxAccId) {
        this.extTxAccId = extTxAccId;
    }

    public Long getFinFundId() {
        return finFundId;
    }

    public void setFinFundId(Long finFundId) {
        this.finFundId = finFundId;
    }

    public BigDecimal getProfitAmt() {
        return profitAmt;
    }

    public void setProfitAmt(BigDecimal profitAmt) {
        this.profitAmt = profitAmt;
    }

    public Date getProfitDate() {
        return profitDate;
    }

    public void setProfitDate(Date profitDate) {
        this.profitDate = profitDate;
    }

    public Date getProfitDateFrom() {
        return profitDateFrom;
    }

    public void setProfitDateFrom(Date profitDateFrom) {
        this.profitDateFrom = profitDateFrom;
    }

    public Date getProfitDateTo() {
        return profitDateTo;
    }

    public void setProfitDateTo(Date profitDateTo) {
        this.profitDateTo = profitDateTo;
    }
}
