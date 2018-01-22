package com.xinyunlian.jinfu.product.dto;

import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.product.enums.EFineType;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.product.enums.ELoanProductType;
import com.xinyunlian.jinfu.product.enums.EViolateType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by JL on 2016/9/2.
 */
public class LoanProductDetailDto implements Serializable{

    private String productId;

    private String productName;

    private ELoanProductType productType;

    private String termLen;

    private ETermType termType;

    private BigDecimal intrRate;

    private EIntrRateType intrRateType;

    private Boolean prepay;

    private ERepayMode repayMode;

    private BigDecimal loanAmtMax;

    private BigDecimal loanAmtMin;

    private BigDecimal repayAmtMin;

    private Integer minIntrDays;

    private EViolateType violateType;

    private BigDecimal violateValue;

    private EFineType fineType;

    private BigDecimal fineValue;

    private String serviceFeeRt;

    private String serviceFeeMonthRt;

    private Integer defaultAmt;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ELoanProductType getProductType() {
        return productType;
    }

    public void setProductType(ELoanProductType productType) {
        this.productType = productType;
    }

    public String getTermLen() {
        return termLen;
    }

    public void setTermLen(String termLen) {
        this.termLen = termLen;
    }

    public ETermType getTermType() {
        return termType;
    }

    public void setTermType(ETermType termType) {
        this.termType = termType;
    }

    public BigDecimal getIntrRate() {
        return intrRate;
    }

    public void setIntrRate(BigDecimal intrRate) {
        this.intrRate = intrRate;
    }

    public EIntrRateType getIntrRateType() {
        return intrRateType;
    }

    public void setIntrRateType(EIntrRateType intrRateType) {
        this.intrRateType = intrRateType;
    }

    public Boolean getPrepay() {
        return prepay;
    }

    public void setPrepay(Boolean prepay) {
        this.prepay = prepay;
    }

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
    }

    public BigDecimal getLoanAmtMax() {
        return loanAmtMax;
    }

    public void setLoanAmtMax(BigDecimal loanAmtMax) {
        this.loanAmtMax = loanAmtMax;
    }

    public BigDecimal getLoanAmtMin() {
        return loanAmtMin;
    }

    public void setLoanAmtMin(BigDecimal loanAmtMin) {
        this.loanAmtMin = loanAmtMin;
    }

    public BigDecimal getRepayAmtMin() {
        return repayAmtMin;
    }

    public void setRepayAmtMin(BigDecimal repayAmtMin) {
        this.repayAmtMin = repayAmtMin;
    }

    public Integer getMinIntrDays() {
        return minIntrDays;
    }

    public void setMinIntrDays(Integer minIntrDays) {
        this.minIntrDays = minIntrDays;
    }

    public EViolateType getViolateType() {
        return violateType;
    }

    public void setViolateType(EViolateType violateType) {
        this.violateType = violateType;
    }

    public BigDecimal getViolateValue() {
        return violateValue;
    }

    public void setViolateValue(BigDecimal violateValue) {
        this.violateValue = violateValue;
    }

    public EFineType getFineType() {
        return fineType;
    }

    public void setFineType(EFineType fineType) {
        this.fineType = fineType;
    }

    public BigDecimal getFineValue() {
        return fineValue;
    }

    public void setFineValue(BigDecimal fineValue) {
        this.fineValue = fineValue;
    }

    public String getServiceFeeRt() {
        return serviceFeeRt;
    }

    public void setServiceFeeRt(String serviceFeeRt) {
        this.serviceFeeRt = serviceFeeRt;
    }

    public String getServiceFeeMonthRt() {
        return serviceFeeMonthRt;
    }

    public void setServiceFeeMonthRt(String serviceFeeMonthRt) {
        this.serviceFeeMonthRt = serviceFeeMonthRt;
    }

    public Integer getDefaultAmt() {
        return defaultAmt;
    }

    public void setDefaultAmt(Integer defaultAmt) {
        this.defaultAmt = defaultAmt;
    }
}
