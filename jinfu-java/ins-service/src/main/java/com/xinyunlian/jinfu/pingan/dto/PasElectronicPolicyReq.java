package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/14/0014.
 */
public class PasElectronicPolicyReq implements Serializable {
    private static final long serialVersionUID = 3827950029764991536L;

    @JsonProperty("partnerCode")
    private String partnerCode;
    @JsonProperty("policyNo")
    private String policyNo;
    @JsonProperty("endorseNo")
    private String endorseNo;
    @JsonProperty("documentType")
    private String documentType;
    @JsonProperty("applyPolicyNo")
    private String applyPolicyNo;
    @JsonProperty("isElectronic")
    private String isElectronic;
    @JsonProperty("languagePrint")
    private String languagePrint;

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getEndorseNo() {
        return endorseNo;
    }

    public void setEndorseNo(String endorseNo) {
        this.endorseNo = endorseNo;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getApplyPolicyNo() {
        return applyPolicyNo;
    }

    public void setApplyPolicyNo(String applyPolicyNo) {
        this.applyPolicyNo = applyPolicyNo;
    }

    public String getIsElectronic() {
        return isElectronic;
    }

    public void setIsElectronic(String isElectronic) {
        this.isElectronic = isElectronic;
    }

    public String getLanguagePrint() {
        return languagePrint;
    }

    public void setLanguagePrint(String languagePrint) {
        this.languagePrint = languagePrint;
    }
}
