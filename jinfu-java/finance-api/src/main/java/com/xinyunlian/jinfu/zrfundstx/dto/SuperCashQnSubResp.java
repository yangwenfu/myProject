package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dongfangchao on 2016/11/30/0030.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "asset")
public class SuperCashQnSubResp implements Serializable{

    private static final long serialVersionUID = -6941999497748014637L;

    @XmlElement(name = "AccumulativeNAV")
    private BigDecimal accumulativeNAV;
    @XmlElement(name = "FundName")
    private String fundName;
    @XmlElement(name = "FundStatus")
    private String fundStatus;
    @XmlElement(name = "FundCode")
    private String fundCode;
    @XmlElement(name = "UpdateDate")
    private Long updateDate;
    @XmlElement(name = "NAV")
    private BigDecimal nav;
    @XmlElement(name = "FundIncome")
    private BigDecimal fundIncome;
    @XmlElement(name = "Yield")
    private BigDecimal yield;

    public BigDecimal getNav() {
        return nav;
    }

    public BigDecimal getFundIncome() {
        return fundIncome;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public BigDecimal getAccumulativeNAV() {
        return accumulativeNAV;
    }

    public void setAccumulativeNAV(BigDecimal accumulativeNAV) {
        this.accumulativeNAV = accumulativeNAV;
    }

    public void setNav(BigDecimal nav) {
        this.nav = nav;
    }

    public void setFundIncome(BigDecimal fundIncome) {
        this.fundIncome = fundIncome;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(String fundStatus) {
        this.fundStatus = fundStatus;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

}
