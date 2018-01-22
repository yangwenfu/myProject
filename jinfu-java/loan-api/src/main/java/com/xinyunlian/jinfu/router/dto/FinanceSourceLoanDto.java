package com.xinyunlian.jinfu.router.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 */
public class FinanceSourceLoanDto implements Serializable{

    /**
     * 贷款申请编号
     */
    private String applId;

    /**
     * 产品编号
     */
    private String prodId;

    /**
     * 贷款编号
     */
    private BigDecimal loanAmt;

    /**
     * 省编号
     */
    private Integer provinceId;

    /**
     * 城市编号
     */
    private Integer cityId;

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "FinanceSourceLoanDto{" +
                "applId='" + applId + '\'' +
                ", prodId='" + prodId + '\'' +
                ", loanAmt=" + loanAmt +
                ", provinceId=" + provinceId +
                ", cityId=" + cityId +
                '}';
    }
}
