package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public class QuotePriceRequest implements Serializable{
    private static final long serialVersionUID = -8768327228222595590L;

    @JsonProperty("partnerName")
    private String partnerName;

    @JsonProperty("transferCode")
    private String transferCode;

    @JsonProperty("transSerialNo")
    private String transSerialNo;

    @JsonProperty("ebcsPtsPlanInfo")
    private QuotePriceSubRequest quotePriceSubRequest;

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getTransferCode() {
        return transferCode;
    }

    public void setTransferCode(String transferCode) {
        this.transferCode = transferCode;
    }

    public String getTransSerialNo() {
        return transSerialNo;
    }

    public void setTransSerialNo(String transSerialNo) {
        this.transSerialNo = transSerialNo;
    }

    public QuotePriceSubRequest getQuotePriceSubRequest() {
        return quotePriceSubRequest;
    }

    public void setQuotePriceSubRequest(QuotePriceSubRequest quotePriceSubRequest) {
        this.quotePriceSubRequest = quotePriceSubRequest;
    }
}
