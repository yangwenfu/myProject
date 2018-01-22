package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dongfangchao on 2017/7/10/0010.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotePriceDataResultResponse implements Serializable {

    @JsonProperty("totalActualPremium")
    private String totalActualPremium;
    @JsonProperty("premiumRate")
    private String premiumRate;
    @JsonProperty("specialPromise")
    private String specialPromise;
    @JsonProperty("noClaim")
    private String noClaim;
    @JsonProperty("productCode")
    private String productCode;
    @JsonProperty("resultDTOlist")
    private List<QuotePriceResultResponse> resultDTOlist;

    public String getTotalActualPremium() {
        return totalActualPremium;
    }

    public void setTotalActualPremium(String totalActualPremium) {
        this.totalActualPremium = totalActualPremium;
    }

    public String getPremiumRate() {
        return premiumRate;
    }

    public void setPremiumRate(String premiumRate) {
        this.premiumRate = premiumRate;
    }

    public String getSpecialPromise() {
        return specialPromise;
    }

    public void setSpecialPromise(String specialPromise) {
        this.specialPromise = specialPromise;
    }

    public String getNoClaim() {
        return noClaim;
    }

    public void setNoClaim(String noClaim) {
        this.noClaim = noClaim;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public List<QuotePriceResultResponse> getResultDTOlist() {
        return resultDTOlist;
    }

    public void setResultDTOlist(List<QuotePriceResultResponse> resultDTOlist) {
        this.resultDTOlist = resultDTOlist;
    }
}
