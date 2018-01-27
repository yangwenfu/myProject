package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dongfangchao on 2016/11/29/0029.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "asset")
public class SuperCashQueryShareSubResp implements Serializable {
    private static final long serialVersionUID = -4408918056953382167L;

    @XmlElement(name = "FundCode")
    private String fundCode;
    @XmlElement(name = "TotalFundVol")
    private BigDecimal totalFundVol;
    @XmlElement(name = "FundDayIncome")
    private BigDecimal fundDayIncome;
    @XmlElement(name = "AvailableVol")
    private BigDecimal availableVol;
    @XmlElement(name = "TotalFrozenVol")
    private BigDecimal totalFrozenVol;
    @XmlElement(name = "TotalIncome")
    private BigDecimal totalIncome;

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public BigDecimal getTotalFundVol() {
        return totalFundVol;
    }

    public void setTotalFundVol(BigDecimal totalFundVol) {
        this.totalFundVol = totalFundVol;
    }

    public BigDecimal getFundDayIncome() {
        return fundDayIncome;
    }

    public void setFundDayIncome(BigDecimal fundDayIncome) {
        this.fundDayIncome = fundDayIncome;
    }

    public BigDecimal getAvailableVol() {
        return availableVol;
    }

    public void setAvailableVol(BigDecimal availableVol) {
        this.availableVol = availableVol;
    }

    public BigDecimal getTotalFrozenVol() {
        return totalFrozenVol;
    }

    public void setTotalFrozenVol(BigDecimal totalFrozenVol) {
        this.totalFrozenVol = totalFrozenVol;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }
}
