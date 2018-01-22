package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dongfangchao on 2016/12/22/0022.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
public class BankSignApplyResp extends BaseResp{

    private static final long serialVersionUID = -2251784729733732380L;

    @XmlElement(name = "ApplicationNo")
    private String applicationNo;

    @XmlElement(name = "ClearingAgencyCode")
    private String clearingAgencyCode;

    @XmlElement(name = "AcctNameOfInvestorInClearingAgency")
    private String acctNameOfInvestorInClearingAgency;

    @XmlElement(name = "AcctNoOfInvestorInClearingAgency")
    private String acctNoOfInvestorInClearingAgency;

    @XmlElement(name = "CertificateType")
    private String certificateType;

    @XmlElement(name = "CertificateNo")
    private String certificateNo;

    @XmlElement(name = "ClearingAgencyName")
    private String clearingAgencyName;

    @XmlElement(name = "BankErrCode")
    private String bankErrCode;

    @XmlElement(name = "BankErrmsg")
    private String bankErrmsg;

    @XmlElement(name = "UserType")
    private String userType;

    @XmlElement(name = "ProtocolNo")
    private Long protocolNo;

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getClearingAgencyCode() {
        return clearingAgencyCode;
    }

    public void setClearingAgencyCode(String clearingAgencyCode) {
        this.clearingAgencyCode = clearingAgencyCode;
    }

    public String getAcctNameOfInvestorInClearingAgency() {
        return acctNameOfInvestorInClearingAgency;
    }

    public void setAcctNameOfInvestorInClearingAgency(String acctNameOfInvestorInClearingAgency) {
        this.acctNameOfInvestorInClearingAgency = acctNameOfInvestorInClearingAgency;
    }

    public String getAcctNoOfInvestorInClearingAgency() {
        return acctNoOfInvestorInClearingAgency;
    }

    public void setAcctNoOfInvestorInClearingAgency(String acctNoOfInvestorInClearingAgency) {
        this.acctNoOfInvestorInClearingAgency = acctNoOfInvestorInClearingAgency;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getClearingAgencyName() {
        return clearingAgencyName;
    }

    public void setClearingAgencyName(String clearingAgencyName) {
        this.clearingAgencyName = clearingAgencyName;
    }

    public String getBankErrCode() {
        return bankErrCode;
    }

    public void setBankErrCode(String bankErrCode) {
        this.bankErrCode = bankErrCode;
    }

    public String getBankErrmsg() {
        return bankErrmsg;
    }

    public void setBankErrmsg(String bankErrmsg) {
        this.bankErrmsg = bankErrmsg;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(Long protocolNo) {
        this.protocolNo = protocolNo;
    }
}
