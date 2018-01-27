package com.xinyunlian.jinfu.finprofithistory.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2016/11/21.
 */
@Entity
@Table(name = "fin_profit_history_summary")
public class FinProfitHistorySumPo implements Serializable{

    private static final long serialVersionUID = -3398635105793466663L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PROFIT_AMT")
    private BigDecimal profitAmt;

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

    public BigDecimal getProfitAmt() {
        return profitAmt;
    }

    public void setProfitAmt(BigDecimal profitAmt) {
        this.profitAmt = profitAmt;
    }

    public BigDecimal getAssetAmt() {
        return assetAmt;
    }

    public void setAssetAmt(BigDecimal assetAmt) {
        this.assetAmt = assetAmt;
    }
}
