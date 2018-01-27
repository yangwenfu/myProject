package com.xinyunlian.jinfu.finfunddetail.entity;

import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import com.xinyunlian.jinfu.finaccbankcard.enums.converter.EFinOrgConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2016/11/21.
 */
@Entity
@Table(name = "fin_fund_detail")
public class FinFundDetailPo implements Serializable {

    private static final long serialVersionUID = -1799516950084726274L;

    @Id
    @Column(name = "FIN_FUND_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long finFundId;

    @Column(name = "FIN_FUND_NAME")
    private String finFundName;

    @Column(name = "FIN_FUND_CODE")
    private String finFundCode;

    @Column(name = "FIN_ORG")
    @Convert(converter = EFinOrgConverter.class)
    private EFinOrg finOrg;

    @Column(name = "YIELD")
    private BigDecimal yield;

    @Column(name = "FOUND_INCOME")
    private BigDecimal foundIncome;

    @Column(name = "REDEEM_DESC")
    private String redeemDesc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE")
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

    public String getFinFundCode() {
        return finFundCode;
    }

    public void setFinFundCode(String finFundCode) {
        this.finFundCode = finFundCode;
    }

    public EFinOrg getFinOrg() {
        return finOrg;
    }

    public void setFinOrg(EFinOrg finOrg) {
        this.finOrg = finOrg;
    }
}
