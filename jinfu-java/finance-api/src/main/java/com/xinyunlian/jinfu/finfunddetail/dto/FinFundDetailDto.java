package com.xinyunlian.jinfu.finfunddetail.dto;

import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public class FinFundDetailDto implements Serializable {

    private static final long serialVersionUID = 4214501632459286342L;
    private Long finFundId;

    private String finFundName;

    private String finFundCode;

    private EFinOrg finOrg;

    private BigDecimal yield;

    private BigDecimal foundIncome;

    private String redeemDesc;

    private Date updateDate;

    public Long getFinFundId() {
        return finFundId;
    }

    public void setFinFundId(Long finFundId) {
        this.finFundId = finFundId;
    }

    public String getFinFundName() {
        return finFundName;
    }

    public void setFinFundName(String finFundName) {
        this.finFundName = finFundName;
    }

    public String getFinFundCode() {
        return finFundCode;
    }

    public void setFinFundCode(String finFundCode) {
        this.finFundCode = finFundCode;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public BigDecimal getFoundIncome() {
        return foundIncome;
    }

    public void setFoundIncome(BigDecimal foundIncome) {
        this.foundIncome = foundIncome;
    }

    public String getRedeemDesc() {
        return redeemDesc;
    }

    public void setRedeemDesc(String redeemDesc) {
        this.redeemDesc = redeemDesc;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public EFinOrg getFinOrg() {
        return finOrg;
    }

    public void setFinOrg(EFinOrg finOrg) {
        this.finOrg = finOrg;
    }
}
