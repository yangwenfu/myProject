package com.xinyunlian.jinfu.product.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.product.enums.EFineType;
import com.xinyunlian.jinfu.product.enums.EViolateType;
import com.xinyunlian.jinfu.product.enums.converter.EFineTypeEnumConverter;
import com.xinyunlian.jinfu.product.enums.converter.EViolateTypeEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by JL on 2016/9/1.
 */
@Entity
@Table(name = "PROD_CREDIT_TYPE")
public class CreditTypeProductPo extends BaseMaintainablePo {
    @Id
    @Column(name = "PROD_ID")
    private String productId;

    @Column(name = "MIN_INTR_DAYS")
    private int minIntrDays;

    @Column(name = "LOAN_AMT_MAX")
    private BigDecimal loanAmtMax;

    @Column(name = "LOAN_AMT_MIN")
    private BigDecimal loanAmtMin;

    @Column(name = "REPAY_AMT_MIN")
    private BigDecimal repayAmtMin;

    @Column(name = "VIOLATE_TYPE")
    @Convert(converter = EViolateTypeEnumConverter.class)
    private EViolateType violateType;

    @Column(name = "VIOLATE_VALUE")
    private BigDecimal violateValue;

    @Column(name = "FINE_TYPE")
    @Convert(converter = EFineTypeEnumConverter.class)
    private EFineType fineType;

    @Column(name = "FINE_VALUE")
    private BigDecimal fineValue;

    @Column(name = "SERVICE_FEE_RT")
    private String serviceFeeRt;

    @Column(name = "SERVICE_FEE_MONTH_RT")
    private String serviceFeeMonthRt;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getMinIntrDays() {
        return minIntrDays;
    }

    public void setMinIntrDays(int minIntrDays) {
        this.minIntrDays = minIntrDays;
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

    public BigDecimal getFineValue() {
        return fineValue;
    }

    public void setFineValue(BigDecimal fineValue) {
        this.fineValue = fineValue;
    }

    public EFineType getFineType() {
        return fineType;
    }

    public void setFineType(EFineType fineType) {
        this.fineType = fineType;
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
}
