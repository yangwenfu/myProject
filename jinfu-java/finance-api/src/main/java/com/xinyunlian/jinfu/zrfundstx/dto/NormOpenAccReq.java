package com.xinyunlian.jinfu.zrfundstx.dto;

/**
 * 个人普通开户请求dto
 * Created by dongfangchao on 2016/11/21.
 */
public class NormOpenAccReq extends BaseReq{
    private static final long serialVersionUID = 1326196461880366617L;

    private String applicationNo;

    private String ClearingAgencyCode;

    private String AcctNameOfInvestorInClearingAgency;

    private String AcctNoOfInvestorInClearingAgency;

    private String InvestorName;

    private String CertificateType;

    private String CertificateNo;

    private Long CertValidDate;

    private Long Sex;

    private String EmailAddress;

    private String MobileTelNo;

    private String OfficeTelNo;

    private String FaxNo;

    private String Address;

    private Long PostCode;

    private Long ProtocolNo;

    private String UserType;

    private String OtherUserId;

    private Long BranchCode;

    private String FundManagerCode;

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
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

    public String getInvestorName() {
        return InvestorName;
    }

    public void setInvestorName(String investorName) {
        InvestorName = investorName;
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

    public Long getCertValidDate() {
        return CertValidDate;
    }

    public void setCertValidDate(Long certValidDate) {
        CertValidDate = certValidDate;
    }

    public Long getSex() {
        return Sex;
    }

    public void setSex(Long sex) {
        Sex = sex;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getMobileTelNo() {
        return MobileTelNo;
    }

    public void setMobileTelNo(String mobileTelNo) {
        MobileTelNo = mobileTelNo;
    }

    public String getOfficeTelNo() {
        return OfficeTelNo;
    }

    public void setOfficeTelNo(String officeTelNo) {
        OfficeTelNo = officeTelNo;
    }

    public String getFaxNo() {
        return FaxNo;
    }

    public void setFaxNo(String faxNo) {
        FaxNo = faxNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Long getPostCode() {
        return PostCode;
    }

    public void setPostCode(Long postCode) {
        PostCode = postCode;
    }

    public Long getProtocolNo() {
        return ProtocolNo;
    }

    public void setProtocolNo(Long protocolNo) {
        ProtocolNo = protocolNo;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getOtherUserId() {
        return OtherUserId;
    }

    public void setOtherUserId(String otherUserId) {
        OtherUserId = otherUserId;
    }

    public Long getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(Long branchCode) {
        BranchCode = branchCode;
    }

    public String getFundManagerCode() {
        return FundManagerCode;
    }

    public void setFundManagerCode(String fundManagerCode) {
        FundManagerCode = fundManagerCode;
    }
}
