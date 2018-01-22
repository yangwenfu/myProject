package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
public class QunarApplyContBaseInfoReq implements Serializable{
    private static final long serialVersionUID = -2359905119518537145L;

    @JsonProperty("applyPolicyNo")
    private String applyPolicyNo;
    @JsonProperty("productCode")
    private String productCode;
    @JsonProperty("totalInsuredAmount")
    private BigDecimal totalInsuredAmount;
    @JsonProperty("inputNetworkFlag")
    private String inputNetworkFlag;
    @JsonProperty("amountCurrencyCode")
    private String amountCurrencyCode;
    @JsonProperty("totalActualPremium")
    private BigDecimal totalActualPremium;
    @JsonProperty("premiumCurrencyCode")
    private String premiumCurrencyCode;
    @JsonProperty("insuranceBeginDate")
    private Date insuranceBeginDate;

    public String getApplyPolicyNo() {
        return applyPolicyNo;
    }

    public void setApplyPolicyNo(String applyPolicyNo) {
        this.applyPolicyNo = applyPolicyNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getAmountCurrencyCode() {
        return amountCurrencyCode;
    }

    public void setAmountCurrencyCode(String amountCurrencyCode) {
        this.amountCurrencyCode = amountCurrencyCode;
    }

    public BigDecimal getTotalInsuredAmount() {
        return totalInsuredAmount;
    }

    public void setTotalInsuredAmount(BigDecimal totalInsuredAmount) {
        this.totalInsuredAmount = totalInsuredAmount;
    }

    public BigDecimal getTotalActualPremium() {
        return totalActualPremium;
    }

    public void setTotalActualPremium(BigDecimal totalActualPremium) {
        this.totalActualPremium = totalActualPremium;
    }

    public String getPremiumCurrencyCode() {
        return premiumCurrencyCode;
    }

    public void setPremiumCurrencyCode(String premiumCurrencyCode) {
        this.premiumCurrencyCode = premiumCurrencyCode;
    }

    public Date getInsuranceBeginDate() {
        return insuranceBeginDate;
    }

    public void setInsuranceBeginDate(Date insuranceBeginDate) {
        this.insuranceBeginDate = insuranceBeginDate;
    }

    public String getInputNetworkFlag() {
        return inputNetworkFlag;
    }

    public void setInputNetworkFlag(String inputNetworkFlag) {
        this.inputNetworkFlag = inputNetworkFlag;
    }
}
