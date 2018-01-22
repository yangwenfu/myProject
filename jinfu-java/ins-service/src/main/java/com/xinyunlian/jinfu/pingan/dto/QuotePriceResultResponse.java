package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotePriceResultResponse implements Serializable{
    private static final long serialVersionUID = -2346613955666361294L;

    @JsonProperty("PLANCODE")
    private String planCode;
    @JsonProperty("PLANNAME")
    private String planName;
    @JsonProperty("INSUREDAMOUNT")
    private BigDecimal insuredAmount;
    @JsonProperty("INSUREDPREMIUM")
    private BigDecimal insuredPremium;
    @JsonProperty("INSUREDGRADECODE")
    private String insuredGradeCode;
    @JsonProperty("ISMAINRISK")
    private String isMainRisk;
    @JsonProperty("ISTYPHOONCONTROLAREA")
    private String isTyphoonControlArea;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public BigDecimal getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(BigDecimal insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public BigDecimal getInsuredPremium() {
        return insuredPremium;
    }

    public void setInsuredPremium(BigDecimal insuredPremium) {
        this.insuredPremium = insuredPremium;
    }

    public String getInsuredGradeCode() {
        return insuredGradeCode;
    }

    public void setInsuredGradeCode(String insuredGradeCode) {
        this.insuredGradeCode = insuredGradeCode;
    }

    public String getIsMainRisk() {
        return isMainRisk;
    }

    public void setIsMainRisk(String isMainRisk) {
        this.isMainRisk = isMainRisk;
    }

    public String getIsTyphoonControlArea() {
        return isTyphoonControlArea;
    }

    public void setIsTyphoonControlArea(String isTyphoonControlArea) {
        this.isTyphoonControlArea = isTyphoonControlArea;
    }
}
