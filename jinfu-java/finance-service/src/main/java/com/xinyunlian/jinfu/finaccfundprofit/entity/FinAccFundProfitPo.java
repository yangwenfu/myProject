package com.xinyunlian.jinfu.finaccfundprofit.entity;

import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import com.xinyunlian.jinfu.finaccbankcard.enums.converter.EFinOrgConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dongfangchao on 2016/11/23.
 */
@Entity
@Table(name = "fin_acc_fund_profit")
public class FinAccFundProfitPo implements Serializable {
    private static final long serialVersionUID = 5920988033593316184L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "EXT_TX_ACC_ID")
    private String extTxAccId;

    @Column(name = "FIN_FUND_ID")
    private Long finFundId;

    @Column(name = "HOLD_ASSET")
    private BigDecimal holdAsset;

    @Column(name = "TOTAL_PROFIT")
    private BigDecimal totalProfit;

    @Column(name = "FIN_ORG")
    @Convert(converter = EFinOrgConverter.class)
    private EFinOrg finOrg;

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

    public BigDecimal getHoldAsset() {
        return holdAsset;
    }

    public void setHoldAsset(BigDecimal holdAsset) {
        this.holdAsset = holdAsset;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public EFinOrg getFinOrg() {
        return finOrg;
    }

    public void setFinOrg(EFinOrg finOrg) {
        this.finOrg = finOrg;
    }
}
