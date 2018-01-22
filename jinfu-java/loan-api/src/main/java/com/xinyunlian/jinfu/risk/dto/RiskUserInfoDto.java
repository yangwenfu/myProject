package com.xinyunlian.jinfu.risk.dto;

import java.io.Serializable;

/**
 * @author willwang
 */
public class RiskUserInfoDto implements Serializable{

    private String userId;

    private String acctNo;

    private String storeName;

    private String acctType;

    private String company;

    private String userCode;

    private String phone;

    private String mobile;

    private String mengNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMengNo() {
        return mengNo;
    }

    public void setMengNo(String mengNo) {
        this.mengNo = mengNo;
    }
}
