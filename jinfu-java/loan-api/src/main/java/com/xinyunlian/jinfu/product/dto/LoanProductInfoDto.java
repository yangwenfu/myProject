package com.xinyunlian.jinfu.product.dto;

import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.product.enums.ELoanProductType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by JL on 2016/9/2.
 */
public class LoanProductInfoDto implements Serializable{

    private String productId;

    private String productName;

    private ELoanProductType productType;

    private String termLen;

    private ETermType termType;

    private BigDecimal intrRate;

    private EIntrRateType intrRateType;

    private Boolean prepay;

    private ERepayMode repayMode;

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
}
