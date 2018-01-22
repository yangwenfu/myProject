package com.xinyunlian.jinfu.product.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.loan.enums.converter.ERepayModeEnumConverter;
import com.xinyunlian.jinfu.loan.enums.converter.ETermTypeEnumConverter;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.product.enums.ELoanProductType;
import com.xinyunlian.jinfu.product.enums.converter.EIntrRateTypeEnumConverter;
import com.xinyunlian.jinfu.product.enums.converter.EProductTypeEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by JL on 2016/9/1.
 */
@Entity
@Table(name = "PROD_INF")
@EntityListeners(IdInjectionEntityListener.class)
public class LoanProductInfoPo extends BaseMaintainablePo {


    @Id
    @Column(name = "PROD_ID")
    private String productId;

    @Column(name = "PROD_NAME")
    private String productName;

    @Column(name = "PROD_TYPE")
    @Convert(converter = EProductTypeEnumConverter.class)
    private ELoanProductType productType;

    @Column(name = "REPAY_MODE")
    @Convert(converter = ERepayModeEnumConverter.class)
    private ERepayMode repayMode;

    @Column(name = "PREPAY")
    private Boolean prepay;

    @Column(name = "TERM_LEN")
    private String termLen;

    @Column(name = "TERM_TYPE")
    @Convert(converter = ETermTypeEnumConverter.class)
    private ETermType termType;

    @Column(name = "INTR_RT")
    private BigDecimal intrRate;

    @Column(name = "DEFAULT_AMT")
    private Integer defaultAmt;

    @Column(name = "INTR_RT_TYPE")
    @Convert(converter = EIntrRateTypeEnumConverter.class)
    private EIntrRateType intrRateType;

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

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
    }

    public Boolean getPrepay() {
        return prepay;
    }

    public void setPrepay(Boolean prepay) {
        this.prepay = prepay;
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

    public Integer getDefaultAmt() {
        return defaultAmt;
    }

    public void setDefaultAmt(Integer defaultAmt) {
        this.defaultAmt = defaultAmt;
    }
}
