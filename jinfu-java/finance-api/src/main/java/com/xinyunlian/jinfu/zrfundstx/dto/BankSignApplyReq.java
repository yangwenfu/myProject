package com.xinyunlian.jinfu.zrfundstx.dto;

/**
 * Created by dongfangchao on 2016/12/22/0022.
 */
public class BankSignApplyReq extends BaseReq{

    private static final long serialVersionUID = -905904111579767572L;

    private String ApplicationNo;

    private String AccoreqSerial;

    private String ClearingAgencyCode;

    private String AcctNameOfInvestorInClearingAgency;

    private String AcctNoOfInvestorInClearingAgency;

    private String CertificateType;

    private String CertificateNo;

    private String SignBizType;

    private String Terminal;

    private Long Sex;

    private String OriginalAcctNoOfInvestorInClearingAgency;

    private String MobileTelNo;

    public String getApplicationNo() {
        return ApplicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        ApplicationNo = applicationNo;
    }

    public String getAccoreqSerial() {
        return AccoreqSerial;
    }

    public void setAccoreqSerial(String accoreqSerial) {
        AccoreqSerial = accoreqSerial;
    }

    public String getClearingAgencyCode() {
        return ClearingAgencyCode;
    }

    public void setClearingAgencyCode(String clearingAgencyCode) {
        ClearingAgencyCode = clearingAgencyCode;
    }

    public String getAcctNameOfInvestorInClearingAgency() {
        return AcctNameOfInvestorInClearingAgency;
    }

    public void setAcctNameOfInvestorInClearingAgency(String acctNameOfInvestorInClearingAgency) {
        AcctNameOfInvestorInClearingAgency = acctNameOfInvestorInClearingAgency;
    }

    public String getAcctNoOfInvestorInClearingAgency() {
        return AcctNoOfInvestorInClearingAgency;
    }

    public void setAcctNoOfInvestorInClearingAgency(String acctNoOfInvestorInClearingAgency) {
        AcctNoOfInvestorInClearingAgency = acctNoOfInvestorInClearingAgency;
    }

    public String getCertificateType() {
        return CertificateType;
    }

    public void setCertificateType(String certificateType) {
        CertificateType = certificateType;
    }

    public String getCertificateNo() {
        return CertificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        CertificateNo = certificateNo;
    }

    public String getSignBizType() {
        return SignBizType;
    }

    public void setSignBizType(String signBizType) {
        SignBizType = signBizType;
    }

    public String getTerminal() {
        return Terminal;
    }

    public void setTerminal(String terminal) {
        Terminal = terminal;
    }

    public Long getSex() {
        return Sex;
    }

    public void setSex(Long sex) {
        Sex = sex;
    }

    public String getOriginalAcctNoOfInvestorInClearingAgency() {
        return OriginalAcctNoOfInvestorInClearingAgency;
    }

    public void setOriginalAcctNoOfInvestorInClearingAgency(String originalAcctNoOfInvestorInClearingAgency) {
        OriginalAcctNoOfInvestorInClearingAgency = originalAcctNoOfInvestorInClearingAgency;
    }

    public String getMobileTelNo() {
        return MobileTelNo;
    }

    public void setMobileTelNo(String mobileTelNo) {
        MobileTelNo = mobileTelNo;
    }
}
