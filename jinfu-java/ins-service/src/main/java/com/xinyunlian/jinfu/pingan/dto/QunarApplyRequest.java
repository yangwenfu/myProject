package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/13/0013.
 */
public class QunarApplyRequest implements Serializable {
    private static final long serialVersionUID = 7232648132201849063L;

    @JsonProperty("partnerName")
    private String partnerName;

    @JsonProperty("departmentCode")
    private String departmentCode;

    @JsonProperty("transferCode")
    private String transferCode;

    @JsonProperty("transSerialNo")
    private String transSerialNo;

    @JsonProperty("contract")
    private QunarApplyContReq contract;

    @JsonProperty("moreInfo")
    private QunarApplyMoreInfoReq moreInfo;

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

    public QunarApplyContReq getContract() {
        return contract;
    }

    public void setContract(QunarApplyContReq contract) {
        this.contract = contract;
    }

    public QunarApplyMoreInfoReq getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(QunarApplyMoreInfoReq moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
}
