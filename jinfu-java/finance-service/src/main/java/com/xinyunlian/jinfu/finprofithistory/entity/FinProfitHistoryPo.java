package com.xinyunlian.jinfu.finprofithistory.entity;

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
@Table(name = "fin_profit_history")
public class FinProfitHistoryPo implements Serializable{
    private static final long serialVersionUID = 6035806455688389121L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "FIN_ORG")
    @Convert(converter = EFinOrgConverter.class)
    private EFinOrg finOrg;

    @Column(name = "EXT_TX_ACC_ID")
    private String extTxAccId;

    @Column(name = "FIN_FUND_ID")
    private Long finFundId;

    @Column(name = "PROFIT_AMT")
    private BigDecimal profitAmt;

    @Column(name = "TOTAL_PROFIT")
    private BigDecimal totalProfit;

    @Column(name = "ASSET_AMT")
    private BigDecimal assetAmt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PROFIT_DATE")
    private Date profitDate;

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

    public Date getProfitDate() {
        return profitDate;
    }

    public void setProfitDate(Date profitDate) {
        this.profitDate = profitDate;
    }

    public String getExtTxAccId() {
        return extTxAccId;
    }

    public void setExtTxAccId(String extTxAccId) {
        this.extTxAccId = extTxAccId;
    }

    public EFinOrg getFinOrg() {
        return finOrg;
    }

    public void setFinOrg(EFinOrg finOrg) {
        this.finOrg = finOrg;
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

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getAssetAmt() {
        return assetAmt;
    }

    public void setAssetAmt(BigDecimal assetAmt) {
        this.assetAmt = assetAmt;
    }
}
